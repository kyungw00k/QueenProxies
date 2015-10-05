package org.proxies.queen.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.proxies.queen.dao.ProxyEntity

@Repository
public interface ProxyRepository extends JpaRepository<ProxyEntity, Long> {
    ProxyEntity findByIpAndPort(String ip, int port)

    List<ProxyEntity> findByAlive(boolean alive)

    List<ProxyEntity> findByCountryCode(String countryCode)

    // FIXME: limit 10
    List<ProxyEntity> findByAliveAndLastModifiedDateBeforeOrderByLastModifiedDateDesc(boolean alive, Date lastModifiedDate)

    List<ProxyEntity> findByTypeOrCountryCodeOrAlive(ProxyEntity.Anonymity type, String countryCode, boolean alive)
}