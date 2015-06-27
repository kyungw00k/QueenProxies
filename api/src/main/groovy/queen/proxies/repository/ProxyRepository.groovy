package queen.proxies.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import queen.proxies.constraint.Anonymity
import queen.proxies.entity.ProxyEntity

@Repository
public interface ProxyRepository extends JpaRepository<ProxyEntity, Long> {
    ProxyEntity findByIpAndPort(String ip, int port)

    List<ProxyEntity> findByAlive(boolean alive)

    List<ProxyEntity> findByCountryCode(String countryCode)

    List<ProxyEntity> findByAliveAndLastModifiedDateBeforeOrderByLastModifiedDateDesc(boolean alive, Date lastModifiedDate)

    List<ProxyEntity> findByTypeOrCountryCodeOrAlive(Anonymity type, String countryCode, boolean alive)
}