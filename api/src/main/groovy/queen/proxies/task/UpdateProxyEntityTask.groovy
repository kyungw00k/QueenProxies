package queen.proxies.task

import static java.util.UUID.randomUUID

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import queen.proxies.constraint.Protocol
import queen.proxies.service.ProxyService
import queen.proxies.util.ExternalApi


/**
 * http://spring.io/guides/gs/scheduling-tasks/
 */
@Component
class UpdateProxyEntityTask {

    @Autowired
    ProxyService proxyService

    String testUrl = "http://google.com"

    @Scheduled(cron = '0 0/5 * * * *')
    def void updateAliveStatus() {
        Thread.start({
            uuid = randomUUID() as String

            println '['+uuid+'] Job updateAliveStatus() Started'

            // See : http://stackoverflow.com/questions/8030908/how-to-check-if-proxy-is-working-in-java
            // Search the item order by `lastModified`
            [false, true].each { asIs ->
                proxyService
                        .rxFindByAliveOrderByLastModifiedDate(asIs)
                        .take(10)
                        .subscribe(
                        { proxyEntries ->
                            proxyEntries.each { entity ->
                                entity.protocols.each { eachProtocol ->
                                    ExternalApi.pingUrlThroughProxyByIpPortAndProtocol(
                                            testUrl,
                                            entity.ip,
                                            entity.port,
                                            eachProtocol == Protocol.http ? Proxy.Type.HTTP : Proxy.Type.SOCKS
                                    ).subscribe(
                                            { toBe ->
                                                if (entity.alive != toBe) {
                                                    entity.alive = toBe
                                                    proxyService
                                                            .rxSave(entity)
                                                            .subscribe(
                                                            { /* onNext      */ },
                                                            { /* onError     */ e -> println e },
                                                            { /* onCompleted */ })
                                                }

                                            },
                                            { /* onError     */ e -> println e },
                                            { /* onCompleted */ }
                                    )
                                }
                            }
                        },
                        { /* onError     */ e -> println e },
                        { /* onCompleted */ })
            }
        })
    }

    @Scheduled(cron = '0 0/10 * * * *')
    def void updateLocationInformation() {
        Thread.start({
            println '['+uuid+'] Job updateLocationInformation() Started'

            proxyService
                    .rxFindByCountryCode('unknown')
                    .take(5)
                    .subscribe(
                    { proxyEntries ->
                        proxyEntries.each { entity ->
                            ExternalApi
                                    .fetchGeolocationByIp(entity.ip)
                                    .subscribe(
                                    { /* onNext      */ item ->
                                        entity.countryCode = item.countryCode
                                        entity.region = item.region
                                        entity.city = item.city
                                    },
                                    { /* onError     */ e -> println e },
                                    { /* onCompleted */
                                        proxyService
                                                .rxSave(entity)
                                                .subscribe(
                                                { savedProxyEntity ->
                                                },
                                                { /* onError     */ e -> println e },
                                                { /* onCompleted */ }
                                        )
                                    }
                            )
                        }
                    },
                    { /* onError     */ e -> println e },
                    { /* onCompleted */ })
        })
    }
}
