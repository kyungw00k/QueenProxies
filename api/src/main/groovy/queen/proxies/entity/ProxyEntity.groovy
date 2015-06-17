package queen.proxies.entity

import com.fasterxml.jackson.annotation.JsonInclude
import queen.proxies.constraint.Protocol
import queen.proxies.constraint.Anonymity

import javax.persistence.*

/**
 * ProxyEntity Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(uniqueConstraints=[@UniqueConstraint(columnNames = ["ip" , "port"])])
class ProxyEntity extends AuditableEntity{
    /**  IP address */
    @Column(nullable = false)
    String ip

    /** Port */
    @Column(nullable = false)
    Integer port

    /** Country code */
    @Column(nullable = true)
    String countryCode

    /** Region */
    @Column(nullable = true)
    String region

    /** City */
    @Column(nullable = true)
    String city

    /** Anonymity */
    @Column(nullable = false)
    Anonymity type

    @ElementCollection(targetClass=Protocol.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="proxy_protocol")
    @Column(name="protocol")
    List<Protocol> protocols

    /** True if the proxy was working when last tested */
    @Column(nullable = false)
    boolean alive = false
//
//    @Column(updatable = false)
//    Date createdDate
//
//    @Column(insertable = false)
//    Date lastModifiedDate
//
//
//    @PrePersist
//    public void prePersist(){
//        setCreatedDate(new Date());
//    }
//
//    @PreUpdate
//    public void preUpdate(){
//        setLastModifiedDate(new Date());
//    }

}
