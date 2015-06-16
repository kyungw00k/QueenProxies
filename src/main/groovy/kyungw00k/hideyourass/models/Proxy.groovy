package kyungw00k.hideyourass.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.PackageScope

import javax.persistence.*

/**
 * kyungw00k.hideyourass.models.Proxy Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
class Proxy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PackageScope
    @JsonIgnore
    long id;

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
    @Column(nullable = true)
    boolean alive
}
