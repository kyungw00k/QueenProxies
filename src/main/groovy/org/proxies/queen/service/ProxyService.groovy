package org.proxies.queen.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.proxies.queen.entity.ProxyEntity
import org.proxies.queen.repository.ProxyRepository

import rx.Observable

@Component
class ProxyService {
    @Autowired
    ProxyRepository proxyRepository

    def Observable<ProxyEntity[]> rxFindByCountryCode(countryCode) {

        return Observable.create({ observer ->
            try {
                observer.onNext(proxyRepository.findByCountryCode((String)countryCode));
                observer.onCompleted();
            } catch (e) {
                observer.onError(e);
            }
        })
    }
    def Observable<ProxyEntity[]> rxFindByAliveAndLastModifiedDateBeforeOrderByLastModifiedDateDesc(boolean alive, Date baseDate) {

        return Observable.create({ observer ->
            try {
                observer.onNext(proxyRepository.findByAliveAndLastModifiedDateBeforeOrderByLastModifiedDateDesc(alive, baseDate));
                observer.onCompleted();
            } catch (e) {
                observer.onError(e);
            }
        })
    }

    def Observable<ProxyEntity[]> rxFindByTypeOrCountryCodeOrAlive(ProxyEntity.Anonymity type, countryCode, boolean alive) {

        return Observable.create({ observer ->
            try {
                def proxies
                if (type || alive || countryCode) {
                    proxies = proxyRepository.findByTypeOrCountryCodeOrAlive(
                            (ProxyEntity.Anonymity) type,
                            (String)countryCode,
                            (boolean) alive
                    )
                } else {
                    proxies = proxyRepository.findAll()
                }

                observer.onNext(proxies);
                observer.onCompleted();
            } catch (e) {
                observer.onError(e);
            }
        })
    }

    def Observable<ProxyEntity> rxSave(ProxyEntity proxyEntity) {
        return Observable.create({ observer ->
            try {
                proxyRepository.save(proxyEntity);
                observer.onNext(proxyEntity);
                observer.onCompleted();
            } catch (e) {
                observer.onError(e);
            }
        })
    }
}
