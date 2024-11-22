package security.com.securityjwt.jwt.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.com.securityjwt.entity.Member;
import security.com.securityjwt.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomerMemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("userId: {}",userId);
        Member member = memberRepository.findByUserId(userId);
        if (member != null) {
            return new CustomMemberDetails(member);
        }
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
    }
}
