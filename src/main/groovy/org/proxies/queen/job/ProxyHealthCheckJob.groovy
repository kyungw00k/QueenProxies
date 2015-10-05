package org.proxies.queen.job

import groovy.util.logging.Slf4j
import org.proxies.queen.dao.ProxyEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import org.proxies.queen.service.ProxyService
import org.proxies.queen.util.ExternalApi

/**
 * http://spring.io/guides/gs/scheduling-tasks/
 */
@Component
@Slf4j
class ProxyHealthCheckJob {
    @Autowired
    ProxyService proxyService

    String testUrl = "http://google.com"

    @Scheduled(cron = '0 0/5 * * * *')
    def void updateAliveStatus() {
        // See : http://stackoverflow.com/questions/8030908/how-to-check-if-proxy-is-working-in-java
        // Search the item order by `lastModified`
        [false, true].each { asIs ->
//            Thread.start({
            proxyService
                    // FIXME: fix the base date to `yesterday`
                    .rxFindByAliveAndLastModifiedDateBeforeOrderByLastModifiedDateDesc(asIs, new Date())
                    .take(10)
                    .subscribe(
                    { proxyEntries ->
                        log.info 'there are ' + proxyEntries.size() + ' proxies in the DB'
                        log.info 'in subscribe section, '
                        proxyEntries.each { entity ->
                            entity.protocols.each { eachProtocol ->
                                log.info 'do update proxy ' + entity.ip + ':' + entity.port + ' if is alive'
                                ExternalApi.pingUrlThroughProxyByIpPortAndProtocol(
                                        testUrl,
                                        entity.ip,
                                        entity.port,
                                        eachProtocol == ProxyEntity.Protocol.http ? Proxy.Type.HTTP : Proxy.Type.SOCKS
                                ).subscribe(
                                        { toBe ->
                                            log.info entity.ip + ':' + entity.port + ' is ' + entity.alive ? 'alive' : 'dead'
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
//            })
        }
    }

    @Scheduled(cron = '0 0/10 * * * *')
    def void updateLocationInformation() {
//        Thread.start({
        proxyService
                .rxFindByCountryCode('unknown')
                .take(5)
                .subscribe(
                { proxyEntries ->
                    log.info 'found ' + proxyEntries.size() + ' unknown proxies in the DB'
                    proxyEntries.each { entity ->
                        log.info 'do update location of proxy ' + entity.ip + ':' + entity.port
                        ExternalApi
                                .fetchGeolocationByIp(entity.ip)
                                .subscribe(
                                { /* onNext      */ item ->
                                    log.info 'fetched location of proxy ' + entity.ip + ':' + entity.port
                                    entity.countryCode = item.countryCode
                                    entity.region = item.region
                                    entity.city = item.city
                                },
                                { /* onError     */ e -> println e },
                                { /* onCompleted */
                                    log.info 'try to update location of proxy ' + entity.ip + ':' + entity.port
                                    proxyService
                                            .rxSave(entity)
                                            .subscribe(
                                            { savedProxyEntity ->
                                                log.info 'updated location of proxy ' + entity.ip + ':' + entity.port
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
//        })
    }
}
