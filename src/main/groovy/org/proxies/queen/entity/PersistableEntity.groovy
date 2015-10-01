package org.proxies.queen.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.*

@MappedSuperclass
class PersistableEntity extends AbstractPersistable<Long> {
    @Version
    @JsonIgnore
    Long version;
}
