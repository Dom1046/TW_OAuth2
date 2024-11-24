package security.com.securityjwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.com.securityjwt.dto.JoinRequestDTO;
import security.com.securityjwt.entity.Member;
import security.com.securityjwt.entity.MemberRole;
import security.com.securityjwt.entity.embadded.Email;
import security.com.securityjwt.entity.embadded.Nickname;
import security.com.securityjwt.entity.embadded.Password;
import security.com.securityjwt.exception.MemberException;
import security.com.securityjwt.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public void joinProcess(JoinRequestDTO joinRequestDTO) {

        Boolean isExistId = memberRepository.existsByUserId(joinRequestDTO.getUserId());
        Boolean isExistEmail = memberRepository.existsByEmail(new Email(joinRequestDTO.getEmail()));

        if (isExistId) {
            throw MemberException.DUPLICATE_USERID.get();
        }
        if (isExistEmail) {
            throw MemberException.DUPLICATE_EMAIL.get();
        }
        Member data = Member.builder()
                .userId(joinRequestDTO.getUserId())
                .nickname(new Nickname(joinRequestDTO.getNickname()))
                .password(new Password(joinRequestDTO.getPassword(),encoder))
                .email(new Email(joinRequestDTO.getEmail()))
                .role(MemberRole.ROLE_USER)
                .build();

        memberRepository.save(data);
    }
}
