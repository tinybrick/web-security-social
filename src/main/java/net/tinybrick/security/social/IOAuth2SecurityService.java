package net.tinybrick.security.social;

import net.tinybrick.security.authentication.ISecurityService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;

/**
 * Created by ji.wang on 2017-06-05.
 */
public interface IOAuth2SecurityService extends ISecurityService {
    void register(OAuth2Authentication authentication);
}
