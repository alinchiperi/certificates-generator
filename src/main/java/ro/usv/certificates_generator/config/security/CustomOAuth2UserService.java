package ro.usv.certificates_generator.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new HashSet<>();

        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("Email not found");
        }

        if (email.endsWith("@student.usv.ro")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        } else if (email.endsWith("@usm.ro")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SECRETARY"));
        } else {
            throw new RuntimeException("Domain not allowed");
        }

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");

    }
}
