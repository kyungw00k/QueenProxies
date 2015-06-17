package queen.proxies.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import queen.proxies.constraint.Anonymity
import queen.proxies.constraint.Protocol
import queen.proxies.entity.ProxyEntity

@Repository
public interface ProxyRepository extends CrudRepository<ProxyEntity, Long> {
    ProxyEntity findByIpAndPort(String ip, int port)
    List<ProxyEntity> findByType(Anonymity type)
    List<ProxyEntity> findByCountryCode(String countryCode)
    List<ProxyEntity> findByAlive(boolean alive)

    List<ProxyEntity> findByProtocols(List<Protocol> protocols)

    List<ProxyEntity> findByTypeAndCountryCode(Anonymity type, String countryCode)
    List<ProxyEntity> findByTypeAndAlive(Anonymity type, boolean alive)
    List<ProxyEntity> findByCountryCodeAndAlive(String countryCode, boolean alive)

    List<ProxyEntity> findByTypeOrCountryCodeOrAlive(Anonymity type, String countryCode, boolean alive)
}