package security.com.javasecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import security.com.javasecurity.entiry.User;
import security.com.javasecurity.entiry.UserRole;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID userId;
    private String password;
    private String name;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    //JWT 문자열의 내용 반환
    public Map<String, Object> getPayload() {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("userId", userId);
        payloadMap.put("name", name);
        payloadMap.put("eamil", email);
        payloadMap.put("role", role);
        return payloadMap;
    }
}
