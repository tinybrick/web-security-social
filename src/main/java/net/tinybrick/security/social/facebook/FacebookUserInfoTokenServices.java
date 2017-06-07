package net.tinybrick.security.social.facebook;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * Created by ji.wang on 2017-06-06.
 */
public class FacebookUserInfoTokenServices extends UserInfoTokenServices {

    public FacebookUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
        super(userInfoEndpointUrl, clientId);
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        final OAuth2Authentication authentication =  super.loadAuthentication(accessToken);
        return authentication;
    }
}
