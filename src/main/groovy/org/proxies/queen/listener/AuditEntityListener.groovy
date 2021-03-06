package org.proxies.queen.listener

import org.proxies.queen.dao.AuditableEntity

import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class AuditEntityListener {

    @PrePersist
    public void prePersist(AuditableEntity e) {
//        e.setCreatedBy(SecurityUtil.getCurrentUser());
        e.setCreatedDate(new Date());
        e.setLastModifiedDate(e.getCreatedDate());

    }

    @PreUpdate
    public void preUpdate(AuditableEntity e) {
//        e.setLastModifiedBy(SecurityUtil.getCurrentUser());
        e.setLastModifiedDate(new Date());
    }
}
