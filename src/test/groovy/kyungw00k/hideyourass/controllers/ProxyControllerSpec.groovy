package kyungw00k.hideyourass.controllers

import groovy.json.JsonSlurper
import kyungw00k.hideyourass.ApplicationTest
import kyungw00k.hideyourass.repositories.ProxyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

/**
 * ProxyController Test using Spock Framework
 *
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
class ProxyControllerSpec extends Specification {
    @Autowired
    ProxyRepository proxyRepository


    @Value('${local.server.port}')
    int port;

    def jsonSlurper = new JsonSlurper()
    def restTemplate = new TestRestTemplate();


    def "GET /proxies"() {
        given:
        def response = restTemplate.getForEntity("http://localhost:"+port+"/proxies", String.class);
        def jsonObject = jsonSlurper.parseText response.body

        expect:
        assert response.hasBody()
        assert jsonObject != null
        assert jsonObject.requestId.length() == '3090cfed-9b33-46f7-9f1c-55317088d397'.length()
        assert jsonObject.data != null
        assert jsonObject.data.filters != null
        assert jsonObject.data.filters.alive != null
        assert jsonObject.data.proxies != null
    }

    def "POST /proxies"() {
        setup:
        def proxyItem = new kyungw00k.hideyourass.models.Proxy()
        proxyItem.ip = '127.0.0.1'
        proxyItem.port = 8888
        proxyItem.alive = true
        proxyItem.type = 'transparent'
        proxyItem.countryCode = 'KR'

        when:
        def response = restTemplate.postForObject("http://localhost:"+port+"/proxies", proxyItem, kyungw00k.hideyourass.models.Response.class)

        then:
        assert response.requestId.length() == '3090cfed-9b33-46f7-9f1c-55317088d397'.length()
        assert response.data != null
        assert response.data.filters != null
        assert response.data.proxies != null
        assert response.data.proxies.size() == 1
        assert response.data.proxies[0].ip.equals(proxyItem.ip)
        assert response.data.proxies[0].port.equals(proxyItem.port)
        assert response.data.proxies[0].alive.equals(proxyItem.alive)
        assert response.data.proxies[0].type.equals(proxyItem.type)
        assert response.data.proxies[0].countryCode.equals(proxyItem.countryCode)


    }
}
