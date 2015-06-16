package queen.proxies.entity

import queen.proxies.listener.AuditEntityListener

import javax.persistence.*

@MappedSuperclass
@EntityListeners(value = [AuditEntityListener.class])
class AuditableEntity extends PersistableEntity {

//    @ManyToOne
//    @JoinColumn(name = "created_by")
//    private User createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    Date createdDate;

//    @ManyToOne
//    @JoinColumn(name = "last_modified_by")
//    private User lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    Date lastModifiedDate;
}
