package security.com.securityjwt.entity.embadded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Log4j2
public class Password {
    public static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@#!%*?&^])[A-Za-z\\d$@!%#*?&^]{8,}$";
    public static final String ERR_MSG = "비밀번호는 8자 이상, 최소 하나의 영어소문자, 영어 대문자, 특수 문자, 숫자가 포함되어야 합니다.";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Column(nullable = false, name = "password")
    private String value;

    public Password(String password, final PasswordEncoder passwordEncoder) {
        log.info("password: {}",password);
        if (!PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(ERR_MSG);
        }
        this.value = passwordEncoder.encode(password);
    }
}
