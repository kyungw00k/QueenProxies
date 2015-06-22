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
        })
    }

    def static pingUrlThroughProxyByIpPortAndProtocol(String url, String proxyIp, Integer proxyPort, Proxy.Type proxyType) {
        return Observable.create({ observer ->

            try {
                // create a Proxy object
                Proxy proxy = new Proxy(proxyType, new InetSocketAddress(proxyIp, proxyPort))

                // Now use this proxy to create the HttpURLConnection object.
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy)
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.connect();

                // true if this connection works through the proxy
                observer.onNext(connection.usingProxy())
                observer.onCompleted();
            } catch (Exception e) {
                observer.onError(e);
            }
        })
    }
}
