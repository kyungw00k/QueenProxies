package org.proxies.queen.listener

import org.proxies.queen.entity.ApiKeyEntity

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
