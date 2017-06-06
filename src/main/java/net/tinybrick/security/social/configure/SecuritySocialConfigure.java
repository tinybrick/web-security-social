package net.tinybrick.security.social.configure;

/**
 * Created by ji.wang on 2017-05-29.
 */

import net.tinybrick.security.authentication.*;
import net.tinybrick.security.social.IOAuth2SecurityService;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableGlobalMethodSecurity
//@EnableWebSecurity
@EnableAutoConfiguration
@EnableOAuth2Client
//@EnableOAuth2Sso
//@PropertySource(value = "classpath:config/application.yml")
public class SecuritySocialConfigure {
    Logger logger = LoggerFactory.getLogger(getClass());

    //@Autowired protected UserProperties userPreferences;

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
        UserInfoTokenServices tokenServices = new FacebookUserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
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

    class FacebookUserInfoTokenServices extends UserInfoTokenServices{
        public FacebookUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
            super(userInfoEndpointUrl, clientId);
        }

        @Override
        public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
            final OAuth2Authentication authentication =  super.loadAuthentication(accessToken);
            IOAuth2SecurityService securityService = (IOAuth2SecurityService) securityService();
            securityService.register(authentication);

            // TODO: Add hook for authentication
            /*userPreferences.setCredential(new IAuthenticationToken<String>() {
                public String getUsername() {
                    return (String) authentication.getPrincipal();
                }
            });
            userPreferences.setAuthorities(new ArrayList(authentication.getAuthorities()));*/

            return authentication;
        }
    }

    @Autowired(required = false) protected ISecurityService securityService;
    @Bean
    ISecurityService securityService() {
        if(null != securityService){
            return securityService;
        }

        try {
            return new IOAuth2SecurityService() {
                public void register(OAuth2Authentication authentication) {

                }

                public void validate(IAuthenticationToken<?> iAuthenticationToken) throws AuthenticationException {

                }

                public List<Authority<?, ?>> getAuthorities(IAuthenticationToken<?> iAuthenticationToken) {
                    return null;
                }
            };
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AuthenticationException(e.getMessage()) {};
        }
    }
}
