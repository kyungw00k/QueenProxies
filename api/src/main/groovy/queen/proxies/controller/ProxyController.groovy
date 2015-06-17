package queen.proxies.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import queen.proxies.constraint.Anonymity
import queen.proxies.entity.ProxyEntity
import queen.proxies.entity.ResponseEntity
import queen.proxies.repository.ProxyRepository

@RestController
class ProxyController {

    @Autowired
    ProxyRepository proxyRepository

    @RequestMapping(value = "/proxies", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity queries(
            @RequestParam(required = false) String key,
            @RequestParam(required = false) Anonymity type,
            @RequestParam(required = false, defaultValue = "true") boolean alive,
            @RequestParam(required = false) String countryCode
    ) {

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

        if (response.data.filters.type || response.data.filters.alive || response.data.filters.countryCode) {
            response.data.proxies = proxyRepository.findByTypeOrCountryCodeOrAlive(
                    (Anonymity)response.data.filters.type,
                    (String)response.data.filters.countryCode,
                    (boolean)response.data.filters.alive
            )
        } else {
            response.data.proxies = proxyRepository.findAll()
        }

        return response
    }

    @RequestMapping(value = "/proxies", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity save(
            @RequestHeader(required = false) String key,
            @RequestBody(required = true) ProxyEntity proxy
    ) {

        def response = ResponseEntity.newInstance()

        proxy = proxyRepository.save(proxy)

        response.data.proxies = [proxy];
        response.message = 'saved'

        return response
    }
}
