package security.com.javasecurity.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import security.com.javasecurity.entiry.RefreshToken;
import security.com.javasecurity.entiry.RefreshTokenRepository;
import security.com.javasecurity.entiry.TokenResponse;
import security.com.javasecurity.exception.TokenErrorResult;
import security.com.javasecurity.exception.TokenException;
import security.com.javasecurity.jwt.JwtUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Value("${jwt.access-token.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME; // 액세스 토큰 유효기간

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Override
    public TokenResponse reissueAccessToken(String authorizationHeader) {
        String refreshToken = jwtUtil.getTokenFromHeader(authorizationHeader);
        String userId = jwtUtil.getUserIdFromToken(refreshToken);
        RefreshToken existRefreshToken = refreshTokenRepository.findByUserId(UUID.fromString(userId));
        String accessToken = null;

        if (!existRefreshToken.getToken().equals(refreshToken) || jwtUtil.isTokenExpired(refreshToken)) {
            // 리프레쉬 토큰이 다르거나, 만료된 경우
            throw new TokenException(TokenErrorResult.INVALID_REFRESH_TOKEN); // 401 에러를 던져 재로그인을 요청
        } else {
            // 액세스 토큰 재발급
            accessToken = jwtUtil.generateAccessToken(UUID.fromString(userId), ACCESS_TOKEN_EXPIRATION_TIME);
        }

        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}