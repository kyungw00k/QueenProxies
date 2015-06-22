package queen.proxies.controller

import queen.proxies.ApplicationTest
import queen.proxies.constraint.Anonymity
import queen.proxies.constraint.Protocol
import queen.proxies.entity.ProxyEntity
import queen.proxies.entity.ResponseEntity
import queen.proxies.repository.ProxyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
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

    def restTemplate = new TestRestTemplate();

    def "POST /proxies"() {
        setup:
        def proxyItem = ProxyEntity.newInstance()
        proxyItem.ip = '208.80.152.201'
        proxyItem.port = 9000
        proxyItem.alive = true
        proxyItem.type = Anonymity.transparent
        proxyItem.protocols = [Protocol.http]

        when:
        def response = restTemplate.postForObject("http://localhost:" + port + "/proxies", proxyItem, ResponseEntity.class)

        then:
        assert response.requestId.length() == '3090cfed-9b33-46f7-9f1c-55317088d397'.length()
        assert response.data != null
        assert response.data.filters != null
        assert response.data.proxies != null
        assert response.data.proxies.size() == 1
        println response.data.proxies
        assert response.data.proxies[0].ip.equals(proxyItem.ip)
        assert response.data.proxies[0].port == proxyItem.port
        assert response.data.proxies[0].alive == proxyItem.alive
        assert response.data.proxies[0].type == proxyItem.type.toString()
        assert response.data.proxies[0].countryCode.equals('US')
        assert response.data.proxies[0].city.equals('San Francisco')
        assert response.data.proxies[0].region.equals('CA')
    }

    def "GET /proxies"() {
        given:
        def response = restTemplate.getForObject("http://localhost:" + port + "/proxies", ResponseEntity.class);

        expect:
        assert response != null
        assert response.requestId.length() == '3090cfed-9b33-46f7-9f1c-55317088d397'.length()
        assert response.data != null
        assert response.data.filters != null
        assert response.data.proxies != null
    }


}
