package security.com.securityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.com.securityjwt.entity.Member;
import security.com.securityjwt.entity.embadded.Email;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUserId(String userId);
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(Email email);
    Member findByNickname(String nickname);
}
