package security.com.securityjwt.entity.embadded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Log4j2
public class Nickname {
    public static final String REGEX = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_\\\\s]{2,20}$";
    public static final String ERR_MSG = "닉네임은 특수문자를 제외한 2~20자리여야 합니다.";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Column(name = "nickname", nullable = false, length = 30)
    private String value;

    public Nickname(final String nickname) {
        String nicknames = nickname.replaceAll("\\s", "");
        log.info("요청된 nickname : {},new : {}",nickname, nicknames);
        if (!PATTERN.matcher(nicknames).matches()) {
            throw new IllegalArgumentException(ERR_MSG);
        }
        this.value = nickname;
    }
}
