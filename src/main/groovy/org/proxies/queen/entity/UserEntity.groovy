package org.proxies.queen.entity

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table;

/**
 * Created by humphrey on 15. 7. 14..
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table
class UserEntity extends AbstractPersistable<Long> {
    @Column(unique = true, nullable = false)
    String username

    @Column(unique = true, nullable = false)
    String email

    @Column(nullable = false)
    String password_hash

    @OneToOne
    ApiKeyEntity apiKey = ApiKeyEntity.newInstance()

    @Column(nullable = false)
    Boolean approvedAccount

}
