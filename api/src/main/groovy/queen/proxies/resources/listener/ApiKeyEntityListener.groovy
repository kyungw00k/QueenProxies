package queen.proxies.resources.listener

import queen.proxies.resources.entity.ApiKeyEntity

import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class ApiKeyEntityListener {

    @PrePersist
    public void prePersist(ApiKeyEntity e) {
        e.setKey(ApiKeyEntity.generateKey())
    }

    @PreUpdate
    public void preUpdate(ApiKeyEntity e) {
        e.setKey(ApiKeyEntity.generateKey())
    }
}
