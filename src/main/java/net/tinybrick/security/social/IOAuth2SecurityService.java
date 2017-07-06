package net.tinybrick.security.social;

import net.tinybrick.security.authentication.ISecurityService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * Created by ji.wang on 2017-06-05.
 */
public interface IOAuth2SecurityService extends ISecurityService {
    public enum SOCIAL_SOURCE{
        FACEBOOK
    }
    Object register(OAuth2Authentication authentication, SOCIAL_SOURCE source) throws Exception;
}
