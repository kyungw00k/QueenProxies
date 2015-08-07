package queen.proxies.resources.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import queen.proxies.resources.entity.ProxyEntity
import queen.proxies.resources.repository.ProxyRepository
import queen.proxies.resources.constraint.Anonymity
import rx.Observable

/**
 * Created by humphrey on 15. 6. 18..
 */
@Component
class ProxyService {
    @Autowired
    ProxyRepository proxyRepository

    def Observable<List<ProxyEntity>> rxFindByCountryCode(String countryCode) {

        return Observable.create({ observer ->
            try {
                observer.onNext(proxyRepository.findByCountryCode(countryCode));
                observer.onCompleted();
            } catch (Exception e) {
                observer.onError(e);
            }
        })
    }
    def Observable<List<ProxyEntity>> rxfindByAliveAndLastModifiedDateBeforeOrderByLastModifiedDateDesc(boolean alive, baseDate) {

        return Observable.create({ observer ->
            try {
                observer.onNext(proxyRepository.findByAliveAndLastModifiedDateBeforeOrderByLastModifiedDateDesc(alive, baseDate));
                observer.onCompleted();
            } catch (Exception e) {
                observer.onError(e);
            }
        })
    }

    def Observable<List<ProxyEntity>> rxFindByTypeOrCountryCodeOrAlive(Anonymity type, String countryCode, boolean alive) {

        return Observable.create({ observer ->
            try {
                def proxies
                if (type || alive || countryCode) {
                    proxies = proxyRepository.findByTypeOrCountryCodeOrAlive(
                            (Anonymity) type,
                            (String) countryCode,
                            (boolean) alive
                    )
                } else {
                    proxies = proxyRepository.findAll()
                }

                observer.onNext(proxies);
                observer.onCompleted();
            } catch (Exception e) {
                observer.onError(e);
            }
        })
    }

    def Observable<ProxyEntity> rxSave(ProxyEntity proxyEntity) {
        return Observable.create({ observer ->
            try {
                observer.onNext(proxyRepository.save(proxyEntity))
                observer.onCompleted();
            } catch (Exception e) {
                observer.onError(e);
            }
        })
    }
}
