package kyungw00k.hideyourass.models

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.PackageScope

/**
 * Created by humphrey on 15. 6. 15..
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class Response {
    /** Requested data */
    def data = new Data()

    /** Unique 36-character string, identifying the request */
    String requestId = UUID.randomUUID().toString()

    /** Error or informational message */
    String message
}
