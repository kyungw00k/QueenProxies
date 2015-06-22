package queen.proxies.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import queen.proxies.constraint.Anonymity
import queen.proxies.entity.ProxyEntity

@Repository
public interface ProxyRepository extends CrudRepository<ProxyEntity, Long> {
    ProxyEntity findByIpAndPort(String ip, int port)
    List<ProxyEntity> findByAlive(boolean alive)
    List<ProxyEntity> findByCountryCode(String countryCode)
    List<ProxyEntity> findByAliveOrderByLastModifiedDateDesc(boolean alive)
    List<ProxyEntity> findByTypeOrCountryCodeOrAlive(Anonymity type, String countryCode, boolean alive)
}