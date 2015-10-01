package org.proxies.queen.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.proxies.queen.entity.ProxyEntity
import org.proxies.queen.ApplicationTest

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
        proxyItem.type = Anonymity.anonymous
        proxyItem.countryCode = 'KR'
        proxyItem.protocols = [Protocol.http]

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
        found.type == Anonymity.anonymous
        found.countryCode.equals('KR')
        println found.protocols
        found.protocols[0] == Protocol.http
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
