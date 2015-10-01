package org.proxies.queen.entity

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * DataEntity Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class DataEntity {
    /** Proxies matching requested filter conditions */
    def proxies = [];

    /** Parameters that are common to all proxies */
    def filters = [:]
}
