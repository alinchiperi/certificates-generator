package ro.usv.certificates_generator.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${frontend.url}")

    String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String email = getEmailFromOAuthToken(oauthToken);
            if (!isAllowedEmailDomain(email)) {
                response.sendRedirect("/access-denied");
                return;
            }
        }

        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String getEmailFromOAuthToken(OAuth2AuthenticationToken oauthToken) {
        return oauthToken.getPrincipal().getAttribute("email");

    }

    private boolean isAllowedEmailDomain(String email) {
        return email != null && email.endsWith("@student.usv.ro");
    }
}
