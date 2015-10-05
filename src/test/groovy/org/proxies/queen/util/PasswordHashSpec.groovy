package org.proxies.queen.util

import org.proxies.queen.ApplicationTest
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@IntegrationTest
class PasswordHashSpec extends Specification {
    def "Encode"() {
        given:
        def password = "X8YDLXhacVkg2Y"

        when:
        def encode = PasswordHash.encode(password)

        then:
        encode != PasswordHash.encode('password2')
    }

    def "Decode"() {
        given:
        def password = "v2xtuUEbiWoY4L"

        when:
        def encode = PasswordHash.encode(password)

        then:
        password == PasswordHash.decode(encode)
    }
}
