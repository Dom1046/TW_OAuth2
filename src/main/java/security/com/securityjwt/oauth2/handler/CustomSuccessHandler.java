package security.com.securityjwt.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import security.com.securityjwt.entity.TokenCategory;
import security.com.securityjwt.jwt.util.JWTUtil;
import security.com.securityjwt.oauth2.dto.CustomOAuth2Member;
import security.com.securityjwt.service.RefreshTokenService;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.jwt.access-token-validity-in-milliseconds}") private Long accessTokenValidity;
    @Value("${spring.jwt.refresh-token-validity-in-milliseconds}") private Long accessRefreshTokenValidity;

    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        //OAuth2User
        CustomOAuth2Member customUserDetails = (CustomOAuth2Member) authentication.getPrincipal();

        // id랑 email 꺼내기
        String userId = customUserDetails.getName();
        String email = customUserDetails.getEmail();

        //권한 꺼내기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // payload 만들기
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("userId", userId);
        payloadMap.put("email", email);
        payloadMap.put("role", role);
        payloadMap.put("category", TokenCategory.ACCESS_TOKEN.name());

        //토큰 생성
        String accessToken = jwtUtil.createAccessToken(payloadMap, accessTokenValidity);
        payloadMap.put("category", TokenCategory.REFRESH_TOKEN.name());
        String refreshToken = jwtUtil.createRefreshToken(payloadMap, accessRefreshTokenValidity);
        log.info("OAuth2로그인, 토큰만듬: {}, refresh: {}", accessToken,refreshToken);

        //Refresh redis에 저장
        refreshTokenService.insertInRedis(payloadMap, refreshToken);

        // Access 헤더에 넣고, Refresh 쿠키에 넣기
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(createCookie(refreshToken));
        response.setStatus(HttpStatus.OK.value());

        // 로그인 후 리다이렉트 -> 어떤 페이지로 이동할 서버에서 선택!
        response.sendRedirect("http://localhost:3000/");
    }

    private Cookie createCookie(String refreshCookie) {
        Cookie cookie = new Cookie("RefreshToken", refreshCookie);
        cookie.setMaxAge(3*24 * 60 * 60);
//        cookie.setSecure(true);
        cookie.setPath("/");
//        cookie.setHttpOnly(true);
        return cookie;
    }
}