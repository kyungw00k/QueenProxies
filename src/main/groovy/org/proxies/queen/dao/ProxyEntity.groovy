package org.proxies.queen.dao

import com.fasterxml.jackson.annotation.JsonInclude

import javax.persistence.*

/**
 * ProxyEntity Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(uniqueConstraints = [@UniqueConstraint(columnNames = ["ip", "port"])])
class ProxyEntity extends AuditableEntity {

    enum Anonymity {
        elite,
        anonymous,
        transparent
    }

    enum Protocol {
        http,
        socks4,
        socks5
    }

    /**  IP address */
    @Column(nullable = false)
    String ip

    /** Port */
    @Column(nullable = false)
    Integer port

    /** Country code */
    @Column(nullable = true)
    String countryCode = 'unknown'

    /** Region */
    @Column(nullable = true)
    String region = 'unknown'

    /** City */
    @Column(nullable = true)
    String city = 'unknown'

    /** Anonymity */
    @Column(nullable = false)
    Anonymity type = Anonymity.transparent

    @ElementCollection(targetClass = Protocol.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "proxy_protocol")
    @Column(name = "protocol")
    List<Protocol> protocols

    /** True if the proxy was working when last tested */
    @Column(nullable = false)
    boolean alive = false
}


