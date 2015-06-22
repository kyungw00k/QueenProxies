package queen.proxies.util

import groovy.json.JsonSlurper
import rx.Observable

/**
 * Created by humphrey on 15. 6. 22..
 */
class ExternalApi {
    def static fetchGeolocationByIp(ip) {
        return Observable.create({ observer ->
            try {
                observer.onNext(
                        JsonSlurper.newInstance().parseText(
                                new URL("http://ip-api.com/json/" + ip).getText()
                        )
                )
                observer.onCompleted();
            } catch (Exception e) {
                observer.onError(e);
            }
        });
    }
}
