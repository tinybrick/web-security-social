package net.tinybrick.security.social.facebook;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ji.wang on 2017-07-05.
 */
public class FacebookClientAuthenticationProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {
    public FacebookClientAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public void setTokenServices(ResourceServerTokenServices tokenServices) {
        super.setTokenServices(tokenServices);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return super.attemptAuthentication(request, response);
    }
}
