package org.proxies.queen.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.proxies.queen.entity.ProxyEntity

@Repository
public interface ApiKeyRepository extends JpaRepository<ProxyEntity, Long> {
}