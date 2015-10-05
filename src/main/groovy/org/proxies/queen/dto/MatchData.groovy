package org.proxies.queen.dto

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * MatchData Trasfer Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class MatchData {
    /** Proxies matching requested filter conditions */
    def proxies = [];

    /** Parameters that are common to all proxies */
    def filters = [:]
}
