package net.tinybrick.security.social.facebook;

import net.tinybrick.security.social.IOAuth2SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.Filter;

/**
 * Created by ji.wang on 2017-06-06.
 */
public class FacebookUserInfoTokenServices extends UserInfoTokenServices {
    @Autowired protected IOAuth2SecurityService securityService;

    static String url = "/login/facebook";

    public FacebookUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
        super(userInfoEndpointUrl, clientId);
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        final OAuth2Authentication authentication =  super.loadAuthentication(accessToken);
        securityService.register(authentication);

        // TODO: Add hook for authentication
            /*userPreferences.setCredential(new IAuthenticationToken<String>() {
                public String getUsername() {
                    return (String) authentication.getPrincipal();
                }
            });
            userPreferences.setAuthorities(new ArrayList(authentication.getAuthorities()));*/

        return authentication;
    }
}
