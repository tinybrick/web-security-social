package net.tinybrick.security.social.it;

import org.junit.Rule;
import org.springframework.security.oauth2.client.test.OAuth2ContextSetup;
import org.springframework.security.oauth2.client.test.RestTemplateHolder;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ji.wang on 2017-06-04.
 */
public class OAuth2TestBase implements RestTemplateHolder {
    @Rule
    public OAuth2ContextSetup context = OAuth2ContextSetup.standard(this);

    RestOperations restTemplate = new RestTemplate();

    public void setRestTemplate(RestOperations restOperations) {
        this.restTemplate = restOperations;
    }

    public RestOperations getRestTemplate() {
        return restTemplate;
    }
}

@TestPropertySource(locations = "classpath:config/test.properties")
class FacebookOAuth2Config extends ResourceOwnerPasswordResourceDetails {
    public FacebookOAuth2Config() {
        setAccessTokenUri("https://graph.facebook.com/oauth/access_token");
        setClientId("1916572711894629");
        setUsername("ella_pnxmwat_user@tfbnw.net");
        setPassword("123456ABCDefg");
    }
}

