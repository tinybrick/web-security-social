package net.tinybrick.security.social.unit;

import net.tinybrick.security.configure.SecurityConfiguration;
import net.tinybrick.security.social.TestConfiguration;
import net.tinybrick.security.social.configure.SecuritySocialConfigure;
import net.tinybrick.test.web.unit.ControllerTestBase;
import net.tinybrick.web.configure.ApplicationCoreConfigure;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ji.wang on 2017-06-06.
 */
@SpringBootTest(classes = {
        TestConfiguration.class,
        SecuritySocialConfigure.class,
        SecurityConfiguration.class,
        ApplicationCoreConfigure.class })
//@ContextConfiguration
@TestPropertySource(locations = "classpath:config/test.properties")
public class FacebookAuthenticationTestCase extends ControllerTestBase {
    public String getUsername() {
        return "FACEBOOK\\user\\1";
    }

    @Value("${accessToken}") String accessToken;

    @Test
    public void TestAccessToken() throws Exception {
        ResultActions resultActions;
        resultActions = PUT("/login/facebook/accesstoken/"+accessToken, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
        resultActions.andDo(print()).andExpect(status().isOk());
    }
}
