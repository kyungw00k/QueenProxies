package kyungw00k.hideyourass.repositories

import kyungw00k.hideyourass.models.Proxy
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
public interface ProxyRepository extends CrudRepository<Proxy, Long> {
    List<Proxy> findByTypeAndCountryCodeAndAliveAllIgnoringCase(String type, String countryCode, boolean alive)
}