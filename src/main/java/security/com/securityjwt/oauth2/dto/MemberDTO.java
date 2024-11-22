package security.com.securityjwt.oauth2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private String userId;
    private String password;
    private String nickname;
    private String email;
    private String role;

}