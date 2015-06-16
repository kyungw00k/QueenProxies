package queen.proxies.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import queen.proxies.ApplicationTest
import queen.proxies.constraint.Anonymity
import queen.proxies.constraint.Protocol
import queen.proxies.entity.ProxyEntity
import spock.lang.Specification

/**
 * ProxyRepository Test using Spock Framework
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@IntegrationTest
class ProxyRepositorySpec extends Specification {
    @Autowired
    ProxyRepository proxyRepository

    def "Push Sample Data"() {
        given:
        def proxyItem = ProxyEntity.newInstance()
        proxyItem.ip = '127.0.0.1'
        proxyItem.port = 8888
        proxyItem.alive = true
        proxyItem.type = Anonymity.elite
        proxyItem.countryCode = 'KR'
        proxyItem.protocol = Protocol.http

        when:
        proxyRepository.save(proxyItem)

        then:
        proxyRepository.findByIpAndPort('127.0.0.1', 8888)
    }


    def "Find Sample Data"() {
        given:
        def found = proxyRepository.findByIpAndPort('127.0.0.1', 8888)

        expect:
        found
        found.ip.equals('127.0.0.1')
        found.port.equals(8888)
        found.alive == true
        found.type == Anonymity.elite
        found.countryCode.equals('KR')
        found.protocol == Protocol.http
        found.createdDate
        found.createdDate == found.lastModifiedDate
        found.version == 0
    }

    def "Modify Sample Data"() {
        given:
        def found = proxyRepository.findByIpAndPort('127.0.0.1', 8888)

        when:
        found = found
        found.countryCode = 'FR'
        found = proxyRepository.save(found)

        then:
        found.countryCode != 'KR'
        found.createdDate != found.lastModifiedDate
        found.version == 1
    }
}
