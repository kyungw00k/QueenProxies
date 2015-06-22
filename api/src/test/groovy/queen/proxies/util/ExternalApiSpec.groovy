package queen.proxies.util

import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import queen.proxies.ApplicationTest
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@IntegrationTest
class ExternalApiSpec extends Specification {

    def "fetch geolocation of 208.80.152.201"() {
        expect:

        // http://ip-api.com/json/208.80.152.201
        ExternalApi
                .fetchGeolocationByIp('208.80.152.201')
                .subscribe({
            assert it.status == 'success'
            assert it.city == 'San Francisco'
            assert it.country == 'United States'
            assert it.countryCode == 'US'
            assert it.region == 'CA'
            assert it.regionName == 'California'
            assert it.zip == '94105'
        })
    }
}