package queen.proxies.entity

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@MappedSuperclass
class PersistableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    Long id;

    @Version
    @JsonIgnore
    Long version;
}
