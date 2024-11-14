package security.com.javasecurity.userinfo;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getName();
}