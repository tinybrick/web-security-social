package net.tinybrick.security.social.controllers;

import net.tinybrick.security.authentication.filter.tools.IEncryptionManager;
import net.tinybrick.security.social.IOAuth2SecurityService;
import net.tinybrick.security.social.facebook.FacebookUserInfoTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ji.wang on 2017-06-06.
 */
@RestController
@RequestMapping("/login")
public class SocialLoginController {
    final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    @Autowired
    protected IOAuth2SecurityService securityService;

    @Autowired
    FacebookUserInfoTokenServices facebookUserInfoTokenServices;

    @Autowired IEncryptionManager encryptionManager;

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "facebook/accesstoken/{token}",
            consumes = {MediaType.ALL_VALUE },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody
    ResponseEntity<Map<String, Object>> facebook(@PathVariable( value = "token") String accesstoken) throws Exception {
        Map<String, Object> userInfoMap = new HashMap<String, Object>();

        OAuth2Authentication authentication = facebookUserInfoTokenServices.loadAuthentication(accesstoken);

        String token;
        try {
            token = securityService.register(authentication, IOAuth2SecurityService.SOCIAL_SOURCE.FACEBOOK);
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage(), e){};
        }

        userInfoMap.put("token", encryptionManager.encrypt(token));
        return new ResponseEntity<Map<String, Object>>(userInfoMap, HttpStatus.OK);
    }
}
