package queen.proxies.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
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
            @RequestParam(required = false) String authKey,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "true") boolean alive,
            @RequestParam(required = false) String country_code
    ) {

        def response = ResponseEntity.newInstance()

        if (type) {
            response.data.filters.type = type
        }

        if (alive) {
            response.data.filters.alive = alive
        }

        if (country_code) {
            response.data.filters.country_code = country_code
        }

        if (response.data.filters.type || response.data.filters.alive || response.data.filters.country_code) {
            response.data.proxies = proxyRepository.findByTypeOrCountryCodeOrAlive(
                    (String) response.data.filters.type,
                    (String) response.data.filters.country_code,
                    (boolean) response.data.filters.alive
            )
        } else {
            response.data.proxies = proxyRepository.findAll()
        }

        return response
    }

    @RequestMapping(value = "/proxies", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity save(
            @RequestHeader(required = false) String authKey,
            @RequestBody(required = true) ProxyEntity proxy
    ) {

        def response = ResponseEntity.newInstance()

        proxy = proxyRepository.save(proxy)

        response.data.proxies = [proxy];
        response.message = 'saved'

        return response
    }
}
