package queen.proxies.resources.entity

import com.fasterxml.jackson.annotation.JsonInclude
import queen.proxies.resources.repository.ApiKeyRepository

import javax.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable;

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
