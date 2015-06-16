package queen.proxies.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import queen.proxies.ApplicationTest
import queen.proxies.entity.ProxyEntity
import spock.lang.Specification

/**
 * ProxyRepository Test using Spock Framework
 *
 * @see { h t t p s : / / c o d e . g o o g l e . c o m / p / s p o c k / w i k i / W h a t s N e w I n S p o c k 0 _ 2 }
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
        def proxyItem = ProxyEntity.newInstance()
        proxyItem.ip = '127.0.0.1'
        proxyItem.port = 8888
        proxyItem.alive = true
        proxyItem.type = 'transparent'
        proxyItem.countryCode = 'KR'

        when:
        proxyRepository.save(proxyItem)

        then:
        proxyRepository.count() == 1
    }


    def "Find Sample Data"() {
        given:
        def found = proxyRepository.findByTypeOrCountryCodeOrAlive('transparent', 'KR', true)

        expect:
        found.size() == 1
        found.get(0).ip.equals('127.0.0.1')
        found.get(0).port.equals(8888)
        found.get(0).alive.equals(true)
        found.get(0).type.equals('transparent')
        found.get(0).countryCode.equals('KR')
        found.get(0).createdDate
        found.get(0).createdDate == found.get(0).lastModifiedDate
        found.get(0).version == 0
    }

    def "Modify Sample Data"() {
        given:
        def found = proxyRepository.findByTypeOrCountryCodeOrAlive('transparent', 'KR', true)

        when:
        found = found.first()
        found.countryCode = 'FR'
        found = proxyRepository.save(found)

        then:
        found.countryCode != 'KR'
        found.createdDate != found.lastModifiedDate
        found.version == 1
    }
}
