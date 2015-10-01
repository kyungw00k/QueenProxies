package org.proxies.queen.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import org.proxies.queen.entity.ProxyEntity
import org.proxies.queen.entity.ResponseEntity
import org.proxies.queen.repository.ProxyRepository
import org.proxies.queen.service.ProxyService
import org.proxies.queen.util.ExternalApi

@RestController
class ProxyController {

    @Autowired
    ProxyRepository proxyRepository

    @Autowired
    ProxyService proxyService


    @RequestMapping(value = "/proxies", method = RequestMethod.GET)
    @ResponseBody
    DeferredResult<ResponseEntity> queries(
            @RequestParam(required = false) key,
            @RequestParam(required = false) ProxyEntity.Anonymity type,
            @RequestParam(required = false, defaultValue = "true") boolean alive,
            @RequestParam(required = false) countryCode
    ) {

        DeferredResult<ResponseEntity> deferredResult = new DeferredResult()

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

            deferredResult.setResult(response)
        }

        def onError = { e ->
            deferredResult.setErrorResult(e)
        }


        proxyService
                .rxFindByTypeOrCountryCodeOrAlive(type, countryCode, alive)
                .subscribe(onNext, onError)

        return deferredResult
    }


    @RequestMapping(value = "/proxies", method = RequestMethod.POST)
    @ResponseBody
    DeferredResult<ResponseEntity> save(
            @RequestHeader(required = false) key,
            @RequestBody(required = true) ProxyEntity proxyEntity
    ) {

        DeferredResult<ResponseEntity> deferredResult = new DeferredResult()

        def onError = { e ->
            deferredResult.setErrorResult(e)
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

                        deferredResult.setResult(response)
                    }, onError)
        }

        ExternalApi
                .fetchGeolocationByIp(proxyEntity.ip)
                .subscribe(onNext, onError, onCompleted)

        return deferredResult
    }

}
