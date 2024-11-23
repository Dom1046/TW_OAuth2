package security.com.securityjwt.oauth2.response;

import lombok.RequiredArgsConstructor;
import java.util.Map;

@RequiredArgsConstructor
public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        Map<String, Object> response = getResponse();
        Object id = response.get("id");
        return id != null ? id.toString() : "";
    }
    @Override
    public String getEmail() {
        Map<String, Object> response = getResponse();
        Object email = response.get("email");
        return email != null ? email.toString() : "";
    }

    @Override
    public String getName() {
        Map<String, Object> response = getResponse();
        Object name = response.get("name");
        return name != null ? name.toString() : "";
    }
    /**
     * Helper method to safely extract the `response` object from attributes.
     * Throws IllegalArgumentException if `response` is not a valid Map.
     */
    private Map<String, Object> getResponse() {
        Object response = attribute.get("response");
        if (response instanceof Map) {
            return (Map<String, Object>) response;
        }
        throw new IllegalArgumentException("Invalid response structure: 'response' field is missing or not a Map.");
    }
}
