package security.com.securityjwt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import security.com.securityjwt.entity.embadded.Email;
import security.com.securityjwt.entity.embadded.Nickname;
import security.com.securityjwt.entity.embadded.Password;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Embedded
    private Nickname nickname;
    @Embedded
    private Password password;
    @Embedded
    private Email email;
    @Enumerated(EnumType.STRING)
    private MemberRole role=MemberRole.ROLE_USER;

    public Member(String userId, String nickname, String password, String email, PasswordEncoder passwordEncoder){
        this.userId = userId;
        this.nickname = new Nickname(nickname);
        this.password = new Password(password, passwordEncoder);
        this.email = new Email(email);
    }

    public void changeNickname(String nickname){
        this.nickname = new Nickname(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder){
        this.password = new Password(password, passwordEncoder);
    }

    public void changeEmail(String email){
        this.email = new Email(email);
    }

}
