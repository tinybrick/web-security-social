package net.tinybrick.security.social.configure;

/**
 * Created by ji.wang on 2017-05-29.
 */

import net.tinybrick.security.authentication.*;
import net.tinybrick.security.social.IOAuth2SecurityService;
import net.tinybrick.security.social.authentication.MemStorageSecurityService;
import net.tinybrick.security.social.facebook.FacebookUserInfoTokenServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableAutoConfiguration
@EnableOAuth2Client
//@PropertySource(value = "classpath:config/application.yml")
public class SecuritySocialConfigure {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Bean
    public IHttpSecurityConfigure getHttpSecurityConfigure() {
        return new IHttpSecurityConfigure() {
            public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.addFilterBefore(facebookSsoFilter(), BasicAuthenticationFilter.class);
            }
        };
    }

    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }

    //@Bean
    public Filter facebookSsoFilter() {
        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        UserInfoTokenServices tokenServices = facebookUserInfoTokenServices();
        tokenServices.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(tokenServices);
        return facebookFilter;
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    public FacebookUserInfoTokenServices facebookUserInfoTokenServices() {
        return new FacebookUserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
    }

    /*@Autowired(required = false) protected IOAuth2SecurityService securityService;
    @Bean
    IOAuth2SecurityService securityService() throws Exception {
        if(null != securityService){
            return securityService;
        }

        securityService = new MemStorageSecurityService("users.conf");
        return securityService;
    }*/
}
