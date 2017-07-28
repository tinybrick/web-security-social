package net.tinybrick.security.social;

import net.tinybrick.security.social.authentication.MemStorageSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ji.wang on 2017-07-04.
 */
@Configuration
@EnableAutoConfiguration
public class TestConfiguration {
    //@Autowired(required = false) protected IOAuth2SecurityService securityService;
    @Bean
    IOAuth2SecurityService securityService() throws Exception {
        /*if(null != securityService){
            return securityService;
        }*/

        IOAuth2SecurityService securityService = new MemStorageSecurityService("users.conf");
        return securityService;
    }
}
