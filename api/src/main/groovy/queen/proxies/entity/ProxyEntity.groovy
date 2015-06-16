package queen.proxies.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.PackageScope

import javax.persistence.*

/**
 * ProxyEntity Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
class ProxyEntity extends AuditableEntity{
    /**  IP address */
    @Column(unique = true, nullable = false)
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

    /** Type */
    @Column(nullable = false)
    String type

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
