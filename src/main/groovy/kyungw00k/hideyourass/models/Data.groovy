package kyungw00k.hideyourass.models

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.PackageScope

/**
 * kyungw00k.hideyourass.models.Data Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class Data {
    /** Proxies matching requested filter conditions */
    def proxies = [];

    /** Parameters that are common to all proxies */
    def filters = [:]
}
