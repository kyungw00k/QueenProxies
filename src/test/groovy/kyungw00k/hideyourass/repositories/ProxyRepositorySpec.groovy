package kyungw00k.hideyourass.repositories

import kyungw00k.hideyourass.ApplicationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * ProxyRepository Test using Spock Framework
 *
 * @see {https://code.google.com/p/spock/wiki/WhatsNewInSpock0_2}
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@IntegrationTest
class ProxyRepositorySpec extends Specification {
    @Autowired
    ProxyRepository proxyRepository

    def "Is Working?"() {
        expect:
        proxyRepository.count() == 0
    }

    def "Push Sample Data"() {
        given:
        def proxyItem = new kyungw00k.hideyourass.models.Proxy()
        proxyItem.ip = '127.0.0.1'
        proxyItem.port = 8888
        proxyItem.alive = true
        proxyItem.type = 'transparent'
        proxyItem.countryCode = 'KR'

        proxyRepository.save(proxyItem)

        expect:
        proxyRepository.count() == 1
    }


    def "Find Sample Data"() {
        given:
        def found = proxyRepository.findByTypeAndCountryCodeAndAliveAllIgnoringCase('transparent', 'KR', true)

        expect:
        found.size() == 1
        found.get(0).ip.equals('127.0.0.1')
        found.get(0).port.equals(8888)
        found.get(0).alive.equals(true)
        found.get(0).type.equals('transparent')
        found.get(0).countryCode.equals('KR')
    }
}
