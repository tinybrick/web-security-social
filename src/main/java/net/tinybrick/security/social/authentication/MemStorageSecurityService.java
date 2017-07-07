package net.tinybrick.security.social.authentication;

import net.tinybrick.security.authentication.Authority;
import net.tinybrick.security.authentication.SimpleSecurityService;
import net.tinybrick.security.social.IOAuth2SecurityService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.net.URLEncoder;
import java.util.*;

/**
 * Created by ji.wang on 2017-06-06.
 */
public class MemStorageSecurityService extends SimpleSecurityService implements IOAuth2SecurityService<String> {
    public MemStorageSecurityService(String fileName) throws Exception {
        super(fileName);
    }

    public String registerSocialUser(OAuth2Authentication authentication, SOCIAL_SOURCE source) throws Exception {
        String principal = authentication.getPrincipal().toString();
        String password = String.valueOf(new Date().getTime());

        Map<String, List<Authority<?, ?>>> item = authMap.get(principal);
        if(null == item) {
            item = new HashMap<String, List<Authority<?, ?>>>();
        }
        else{
            item.clear();
        }
        item.put(password, new ArrayList(authentication.getAuthorities()));
        authMap.put(principal, item);

        String token = source.toString() + "\\" + URLEncoder.encode(principal, "UTF-8") + ":" + password;

        return token;
    }
}
