package queen.proxies.resources.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.*

@MappedSuperclass
class PersistableEntity extends AbstractPersistable<Long> {
    @Version
    @JsonIgnore
    Long version;
}
