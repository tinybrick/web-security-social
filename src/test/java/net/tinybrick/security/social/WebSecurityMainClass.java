package net.tinybrick.security.social;

import net.tinybrick.security.configure.SecurityConfiguration;
import net.tinybrick.security.social.configure.SecuritySocialConfigure;
import net.tinybrick.web.configure.ApplicationCoreConfigure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Hello world!
 */

@EnableWebMvc
@ComponentScan
@Import(value = {
        TestConfiguration.class,
        SecuritySocialConfigure.class,
        SecurityConfiguration.class,
        ApplicationCoreConfigure.class})
public class WebSecurityMainClass {
    static final Logger logger = LogManager.getLogger(WebSecurityMainClass.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebSecurityMainClass.class);
        app.setBannerMode(Mode.OFF);
        app.run(args);

        logger.info("Server is running...");
    }
}