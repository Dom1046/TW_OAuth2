package security.com.securityjwt.oauth2.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2Member implements OAuth2User {

    private final MemberDTO memberDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add((GrantedAuthority) memberDTO::getRole);
        return collection;
    }

    @Override
    public String getName() {
        return memberDTO.getUserId();
    }

    public String getUsername() {
        return memberDTO.getNickname();
    }
}
