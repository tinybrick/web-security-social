package net.tinybrick.security.social.it;

import net.tinybrick.security.social.WebSecurityMainClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by ji.wang on 2017-06-04.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebSecurityMainClass.class)
@WebAppConfiguration
@IntegrationTest({ "server.port:0", "authentication.filter.captcha:false"})
@DirtiesContext
public class SocialSecurityIT extends OAuth2TestBase {
    @Value("${local.server.port}") private int port;

    @Test
    public void testHelloOAuth2WithRole() {
        ResponseEntity<String> entity = getRestTemplate().getForEntity("http://localhost:"+port+"/rest/v1/user", String.class);
        Assert.assertTrue(entity.getStatusCode().is2xxSuccessful());
    }
}
