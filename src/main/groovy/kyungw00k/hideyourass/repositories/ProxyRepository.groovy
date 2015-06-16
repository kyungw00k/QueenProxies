package kyungw00k.hideyourass.repositories

import kyungw00k.hideyourass.models.Proxy
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
public interface ProxyRepository extends CrudRepository<Proxy, Long> {
    List<Proxy> findByType(String type)
    List<Proxy> findByCountryCode(String countryCode)
    List<Proxy> findByAlive(boolean alive)

    List<Proxy> findByTypeAndCountryCode(String type, String countryCode)
    List<Proxy> findByTypeAndAlive(String type, boolean alive)
    List<Proxy> findByCountryCodeAndAlive(String countryCode, boolean alive)

    List<Proxy> findByTypeOrCountryCodeOrAlive(String type, String countryCode, boolean alive)
}