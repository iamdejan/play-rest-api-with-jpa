package utility;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class ResponseUtility {
    public static ObjectNode createResponse(Object response, boolean success) {
        ObjectNode result = Json.newObject();
        result.put("success", success);
        if(response instanceof String) {
            result.put("body", String.valueOf(response));
        } else {
            result.putPOJO("body", response);
        }
        return result;
    }
}
