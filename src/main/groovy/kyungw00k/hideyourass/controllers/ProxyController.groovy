package kyungw00k.hideyourass.controllers

import kyungw00k.hideyourass.models.Proxy
import kyungw00k.hideyourass.models.Response
import kyungw00k.hideyourass.repositories.ProxyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class ProxyController {

    @Autowired
    ProxyRepository proxyRepository

    @RequestMapping(value = "/proxies", method = RequestMethod.GET)
    @ResponseBody
    Response queries(
            @RequestParam(required = false) String authKey,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "true") Boolean alive,
            @RequestParam(required = false) String country_code
    ) {

        def response = new Response()

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
            response.data.proxies = proxyRepository.findByTypeAndProtocolsAndCountryCodeAndAliveAllIgnoringCase(
                    response.data.filters.type,
                    response.data.filters.protocols,
                    response.data.filters.country_code
            )
        } else {
            response.data.proxies = proxyRepository.findAll()
        }

        return response
    }

    @RequestMapping(value = "/proxies", method = RequestMethod.POST)
    @ResponseBody
    Response save(
            @RequestParam(required = false) String authKey,
            @RequestParam(required = true) Proxy proxy
    ) {

        def response = new Response()

        response.data.proxies << proxyRepository.save(proxy)
        response.message 'saved'

        return response
    }
}
