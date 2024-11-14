package security.com.javasecurity.service;


import security.com.javasecurity.dto.TokenResponse;

public interface TokenService {
    TokenResponse reissueAccessToken(String authorizationHeader);
}
