package net.tinybrick.security.social.unit;

import net.tinybrick.security.authentication.UsernamePasswordToken;
import net.tinybrick.security.configure.SecurityConfigure;
import net.tinybrick.security.social.configure.SecuritySocialConfigure;
import net.tinybrick.test.web.unit.ControllerTestBase;

import net.tinybrick.web.configure.ApplicationCoreConfigure;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ji.wang on 2017-06-06.
 */
@SpringApplicationConfiguration(classes = {SecuritySocialConfigure.class, SecurityConfigure.class, ApplicationCoreConfigure.class })
public class FacebookAuthenticationTestCase extends ControllerTestBase {
    @Test
    public void TestAccessToken() throws Exception {
        ResultActions resultActions;
        resultActions = PUT("/login/facebook/accesstoken/EAAbPHNutvmUBAEX80s9GYfo9ivpAPQAqtvQcesuMZCjaHLyxNslGcEcApnidSyfW1VS1pkqoNbwurZA9PU0aRAdUfFZCP2lt8jJpZB77fPf3t8ZCJw7CZANImKmyIiXBYZAAu8b3LUXqHij60at3VUEJglVeEhZBLaLyTpQz2FWEZCwZDZD", MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
        resultActions.andDo(print()).andExpect(status().isOk());
    }
}
