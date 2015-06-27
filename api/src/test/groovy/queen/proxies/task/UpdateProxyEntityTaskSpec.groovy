package queen.proxies.task

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import queen.proxies.ApplicationTest
import queen.proxies.constraint.Anonymity
import queen.proxies.constraint.Protocol
import queen.proxies.entity.ProxyEntity
import queen.proxies.repository.ProxyRepository
import queen.proxies.service.ProxyService
import spock.lang.Specification

/**
 * Created by kyungwook on 15. 6. 26..
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@IntegrationTest
class UpdateProxyEntityTaskSpec extends Specification {
    @Autowired
    UpdateProxyEntityTask updateProxyEntityTask

    @Autowired
    ProxyRepository proxyRepository

    def "test updateAliveStatus()" () {
        given:
        def proxyItem = ProxyEntity.newInstance()
        proxyItem.ip = '185.8.236.17'
        proxyItem.port = 8888
        proxyItem.alive = false
        proxyItem.type = Anonymity.transparent
        proxyItem.protocols = [Protocol.http]
        proxyRepository.save(proxyItem)

        when:
        updateProxyEntityTask.updateAliveStatus()

        then:
        assert proxyRepository
                .findByIpAndPort('185.8.236.17', 8888)
                .alive
    }

    def "test updateLocationInformation()"() {
        given:
        assert proxyRepository
                .findByIpAndPort('185.8.236.17', 8888)
                .countryCode == 'unknown'

        when:
        updateProxyEntityTask.updateLocationInformation()

        then:
        assert proxyRepository
                .findByIpAndPort('185.8.236.17', 8888)
                .countryCode != 'unknown'
    }
}
