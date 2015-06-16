package queen.proxies.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import queen.proxies.entity.ProxyEntity

@Repository
public interface ProxyRepository extends CrudRepository<ProxyEntity, Long> {
    List<ProxyEntity> findByType(String type)
    List<ProxyEntity> findByCountryCode(String countryCode)
    List<ProxyEntity> findByAlive(boolean alive)

    List<ProxyEntity> findByTypeAndCountryCode(String type, String countryCode)
    List<ProxyEntity> findByTypeAndAlive(String type, boolean alive)
    List<ProxyEntity> findByCountryCodeAndAlive(String countryCode, boolean alive)

    List<ProxyEntity> findByTypeOrCountryCodeOrAlive(String type, String countryCode, boolean alive)
}