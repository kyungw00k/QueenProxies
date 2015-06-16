package queen.proxies.entity

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by humphrey on 15. 6. 15..
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseEntity {
    /** Requested data */
    def data = new DataEntity()

    /** Unique 36-character string, identifying the request */
    String requestId = UUID.randomUUID().toString()

    /** Error or informational message */
    String message
}
