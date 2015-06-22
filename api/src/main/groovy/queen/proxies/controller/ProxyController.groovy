package queen.proxies.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import queen.proxies.constraint.Anonymity
import queen.proxies.entity.ProxyEntity
import queen.proxies.entity.ResponseEntity
import queen.proxies.repository.ProxyRepository
import queen.proxies.service.ProxyService
import queen.proxies.util.ExternalApi

@RestController
class ProxyController {

    @Autowired
    ProxyRepository proxyRepository

    @Autowired
    ProxyService proxyService


    @RequestMapping(value = "/proxies", method = RequestMethod.GET)
    @ResponseBody
    DeferredResult<ResponseEntity> queries(
            @RequestParam(required = false) String key,
            @RequestParam(required = false) Anonymity type,
            @RequestParam(required = false, defaultValue = "true") boolean alive,
            @RequestParam(required = false) String countryCode
    ) {

        DeferredResult<ResponseEntity> deffered = new DeferredResult()

        def onNext = {

            def response = ResponseEntity.newInstance()

            if (type) {
                response.data.filters.type = type
            }

            if (alive) {
                response.data.filters.alive = alive
            }

            if (countryCode) {
                response.data.filters.countryCode = countryCode
            }

            response.data.proxies = it

            deffered.setResult(response)
        }

        def onError = { e ->
            deffered.setErrorResult(e)
        }


        proxyService
                .rxFindByTypeOrCountryCodeOrAlive(type, countryCode, alive)
                .subscribe(onNext, onError)

        return deffered
    }


    @RequestMapping(value = "/proxies", method = RequestMethod.POST)
    @ResponseBody
    DeferredResult<ResponseEntity> save(
            @RequestHeader(required = false) String key,
            @RequestBody(required = true) ProxyEntity proxyEntity
    ) {

        DeferredResult<ResponseEntity> deffered = new DeferredResult()

        def onError = { e ->
            deffered.setErrorResult(e)
        }

        def onNext = { item ->
            proxyEntity.countryCode = item.countryCode
            proxyEntity.region = item.region
            proxyEntity.city = item.city
        }

        def onCompleted = {
            proxyService
                    .rxSave(proxyEntity)
                    .subscribe(
                    { savedProxyEntity ->
                        def response = ResponseEntity.newInstance()

                        response.data.proxies = [savedProxyEntity]
                        response.message = 'saved'

                        deffered.setResult(response)
                    }, onError)
        }

        ExternalApi
                .fetchGeolocationByIp(proxyEntity.ip)
                .subscribe(onNext, onError, onCompleted)

        return deffered
    }

}
