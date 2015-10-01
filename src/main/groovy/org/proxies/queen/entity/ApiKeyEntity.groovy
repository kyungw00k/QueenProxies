package org.proxies.queen.entity

import com.fasterxml.jackson.annotation.JsonInclude
import org.proxies.queen.listener.ApiKeyEntityListener

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Table

/**
 * Created by humphrey on 15. 7. 14..
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table
@EntityListeners(value = [ApiKeyEntityListener.class])
class ApiKeyEntity extends AuditableEntity {

    @Column(nullable = false)
    String key

    def static generateKey() {
        def pool = ['a'..'z','A'..'Z',0..9,'_'].flatten()
        Random rand = new Random(System.currentTimeMillis())

        def passChars = (0..31).collect { pool[rand.nextInt(pool.size())] }

        passChars.join()
    }
}
