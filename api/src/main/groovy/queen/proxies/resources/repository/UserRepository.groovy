package queen.proxies.resources.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import queen.proxies.resources.constraint.Anonymity
import queen.proxies.resources.entity.ProxyEntity
import queen.proxies.resources.entity.UserEntity

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}