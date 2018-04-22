package pl.applicationserver.scorefilesmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableAutoConfiguration( exclude = OAuth2ClientAutoConfiguration.class)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
