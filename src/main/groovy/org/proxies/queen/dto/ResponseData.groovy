package org.proxies.queen.dto

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * ResponseData Trasfer Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseData {
    /** Requested data */
    def data = new MatchData()

    /** Unique 36-character string, identifying the request */
    String requestId = UUID.randomUUID().toString()

    /** Error or informational message */
    String message
}
