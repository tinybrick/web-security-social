package net.tinybrick.security.social.facebook;

import net.tinybrick.security.authentication.filter.tools.IEncryptionManager;
import net.tinybrick.security.social.IOAuth2SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

/**
 * Created by ji.wang on 2017-06-06.
 */
public class FacebookUserInfoTokenServices extends UserInfoTokenServices {
    @Autowired
    protected IOAuth2SecurityService securityService;
    @Autowired
    IEncryptionManager encryptionManager;

    public FacebookUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
        super(userInfoEndpointUrl, clientId);
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        final OAuth2Authentication authentication = super.loadAuthentication(accessToken);

        try {
            Object token = securityService.registerSocialUser(authentication, IOAuth2SecurityService.SOCIAL_SOURCE.FACEBOOK);
            ((Map)authentication.getUserAuthentication().getDetails()).put("siteToken", token.toString());
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage(), e){};
        }

        return authentication;
    }
}
