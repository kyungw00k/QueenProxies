package org.proxies.queen.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.proxies.queen.entity.ProxyEntity
import org.proxies.queen.ApplicationTest

import spock.lang.Specification

/**
 * ProxyService Test using Spock Framework
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@IntegrationTest
class ProxyServiceSpec extends Specification {

    @Autowired
    ProxyService proxyService


    def "Test rxSave()"() {
        given:
        def proxyItem = ProxyEntity.newInstance()
        proxyItem.ip = '208.80.152.202'
        proxyItem.port = 8889
        proxyItem.alive = false
        proxyItem.type = Anonymity.elite
        proxyItem.protocols = [Protocol.http]


        expect:
        proxyService
                .rxSave(proxyItem)
                .subscribe({
            assert it.ip == proxyItem.ip
            assert it.port == proxyItem.port
            assert it.alive == proxyItem.alive
            assert it.type == proxyItem.type
        })
    }

    def "Test rxFindByTypeOrCountryCodeOrAlive()"() {
        expect:
        proxyService
                .rxFindByTypeOrCountryCodeOrAlive(Anonymity.elite, null, false)
                .subscribe({
            assert it[0].ip == '208.80.152.202'
            assert it[0].port == 8889
            assert it[0].alive == false
            assert it[0].type == Anonymity.elite
        })
    }
}
