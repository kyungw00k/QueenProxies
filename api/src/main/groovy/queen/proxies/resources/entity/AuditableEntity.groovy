package queen.proxies.resources.entity

import queen.proxies.resources.listener.AuditEntityListener

import javax.persistence.*

@MappedSuperclass
@EntityListeners(value = [AuditEntityListener.class])
class AuditableEntity extends PersistableEntity {

    @ManyToOne
    @JoinColumn(name = "created_by")
    UserEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    Date createdDate;

    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    UserEntity lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    Date lastModifiedDate;
}
