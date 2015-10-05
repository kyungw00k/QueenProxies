package org.proxies.queen.repository

import org.proxies.queen.ApplicationTest
import org.proxies.queen.dao.UserEntity
import org.proxies.queen.util.PasswordHash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * UserRepository Test using Spock Framework
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = ApplicationTest.class)
@IntegrationTest
class UserRepositorySpec extends Specification {

    @Autowired
    UserRepository userRepository

    @Autowired
    ApiKeyRepository apiKeyRepository


    def "Dummy"() {
        given:
        def something = false;

        when:
        something = true

        then:
        something == true
    }

    def "Create"() {
        given:
        def userEntity = UserEntity.newInstance()
        userEntity.username = 'kyungw00k'
        userEntity.email = 'kyungw00k@localhost'
        userEntity.passwordHash = PasswordHash.encode('password')

        when:
        def foundEntity = userRepository.save(userEntity);

        then:
        foundEntity != null
        userEntity.approvedAccount == false
        foundEntity.passwordHash == userEntity.passwordHash
    }

    def "Update"() {
        given:
        def userEntity = userRepository.findByUsername('kyungw00k')

        when:
        userEntity.approvedAccount = true
        userRepository.save(userEntity);

        then:
        def foundEntity = userRepository.findByUsername('kyungw00k')
        foundEntity.username == userEntity.username
        foundEntity.passwordHash == userEntity.passwordHash
        foundEntity.approvedAccount == true
    }

    def "Delete"() {
        given:
        def userEntity = userRepository.findByUsername('kyungw00k')

        when:
        userRepository.delete(userEntity.id)

        then:
        userRepository.findByUsername('kyungw00k') == null
    }
}