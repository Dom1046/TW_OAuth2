package security.com.securityjwt.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import security.com.securityjwt.entity.Member;
import security.com.securityjwt.entity.MemberRole;
import security.com.securityjwt.entity.PasswordGenerator;
import security.com.securityjwt.oauth2.dto.CustomOAuth2Member;
import security.com.securityjwt.oauth2.dto.MemberDTO;
import security.com.securityjwt.oauth2.response.GoogleResponse;
import security.com.securityjwt.oauth2.response.NaverResponse;
import security.com.securityjwt.oauth2.response.OAuth2Response;
import security.com.securityjwt.repository.MemberRepository;

import java.util.UUID;
/*
    리소스 서버로 부터 값을 받아와서
    어플리케이션에서 정제하는 로직
*/
@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }
        String userId = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        Member existMember = memberRepository.findByUserId(userId);

        // 회원이 존재하지 않으면 자동 회원가입
        if (existMember == null) {
            String password = PasswordGenerator.generatePassword();
            Member member = new Member(userId, oAuth2Response.getName(), password, oAuth2Response.getEmail(), passwordEncoder);
            memberRepository.save(member);

            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setUserId(userId);
            memberDTO.setPassword(password);
            memberDTO.setNickname(member.getNickname().getValue());
            memberDTO.setEmail(member.getEmail().getValue());
            memberDTO.setRole(MemberRole.ROLE_USER.name());

            return new CustomOAuth2Member(memberDTO);
        } else {
            // 회원이 존재한다면, 비밀번호 업데이트 후 로그인.
            String password = PasswordGenerator.generatePassword();
            existMember.changePassword(password, passwordEncoder);

            memberRepository.save(existMember);

            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setUserId(existMember.getUserId());
            memberDTO.setPassword(existMember.getPassword().getValue());
            memberDTO.setNickname(existMember.getNickname().getValue());
            memberDTO.setEmail(existMember.getEmail().getValue());
            memberDTO.setRole(existMember.getRole().name());

            return new CustomOAuth2Member(memberDTO);
        }
    }
}
