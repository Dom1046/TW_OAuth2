package security.com.javasecurity.service;


import security.com.javasecurity.entiry.TokenResponse;

public interface TokenService {
    TokenResponse reissueAccessToken(String authorizationHeader);
}
