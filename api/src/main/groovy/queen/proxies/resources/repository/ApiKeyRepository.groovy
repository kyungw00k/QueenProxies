package queen.proxies.resources.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import queen.proxies.resources.entity.ProxyEntity

@Repository
public interface ApiKeyRepository extends JpaRepository<ProxyEntity, Long> {
}