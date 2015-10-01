package org.proxies.queen.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.proxies.queen.ApplicationTest
import org.proxies.queen.entity.ApiKeyEntity
import spock.lang.Specification

/**
 * ApiKeyRepository Test using Spock Framework
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@IntegrationTest
class ApiKeyRepositorySpec extends Specification {
    @Autowired
    ApiKeyRepository apiKeyRepository

    def "Generate Api Key"() {
        given:
        def apiKeyItem = ApiKeyEntity.newInstance()

        when:
        apiKeyRepository.save(apiKeyItem)

        then:
        apiKeyItem.key != null
        apiKeyItem.key.length() == 32
        apiKeyItem.version == 0
        apiKeyItem.createdDate != null
    }
}
