package pl.applicationserver.scorefilesmanager.app.constants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import pl.applicationserver.scorefilesmanager.config.SpringSecurityAuditorAware;

@Configuration
public class AppConstants {
    public static final String TYPE_JOB="job";

    @Bean
    AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}
