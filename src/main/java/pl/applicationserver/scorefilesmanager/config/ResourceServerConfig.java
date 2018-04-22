package pl.applicationserver.scorefilesmanager.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "resource_id";
    private UserDetailsService userDetailsService;

    public ResourceServerConfig(@Qualifier(value = "appUserService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and()
                .authenticationProvider(getAuthenticationProvider())
                .authorizeRequests()
                .mvcMatchers("/user/register/","/register/", "/oauth/token").permitAll()
                .mvcMatchers("/user/me").authenticated()
                .mvcMatchers("/oauth/revoke-token").authenticated()//log off

                .mvcMatchers(HttpMethod.GET,"/user/roles").authenticated()
                .mvcMatchers(HttpMethod.GET,"/user/privileges").authenticated()
                .mvcMatchers(HttpMethod.GET,"/api/instrument").authenticated()
                .mvcMatchers(HttpMethod.GET,"/api/scoreTitle").authenticated()
                .mvcMatchers(HttpMethod.GET,"/api/scoreBookTitle").authenticated()
                .mvcMatchers(HttpMethod.GET,"/api/score/types").authenticated()
                .mvcMatchers(HttpMethod.GET,"/api/jobs/types").authenticated()
                .mvcMatchers(HttpMethod.GET,"/api/jobs/statuses").authenticated()

                .mvcMatchers(HttpMethod.POST,"/api/instrument").hasRole("EDITOR")
                .mvcMatchers(HttpMethod.PUT,"/api/instrument").hasRole("EDITOR")
                .mvcMatchers(HttpMethod.DELETE,"/api/instrument").hasRole("EDITOR")

                .mvcMatchers(HttpMethod.POST,"/api/scoreTitle").hasRole("EDITOR")
                .mvcMatchers(HttpMethod.PUT,"/api/scoreTitle").hasRole("EDITOR")
                .mvcMatchers(HttpMethod.DELETE,"/api/scoreTitle").hasRole("EDITOR")

                .mvcMatchers(HttpMethod.POST,"/api/scoreBookTitle").hasRole("EDITOR")
                .mvcMatchers(HttpMethod.PUT,"/api/scoreBookTitle").hasRole("EDITOR")
                .mvcMatchers(HttpMethod.DELETE,"/api/scoreBookTitle").hasRole("EDITOR")

                .mvcMatchers(HttpMethod.GET,"/api/score/**").hasRole("EDITOR")
                .mvcMatchers(HttpMethod.GET,"/api/storage/fileType").hasAuthority("file_read")
                .mvcMatchers(HttpMethod.GET,"/api/storage/scoreType").hasAuthority("file_read")
                .mvcMatchers(HttpMethod.GET,"/api/storage/scoreType").hasAuthority("file_read")

                .mvcMatchers(HttpMethod.GET,"/api/storage/deleted").hasAnyRole("MODERATOR","ADMIN")
                .mvcMatchers(HttpMethod.GET,"/api/storage/fileList").hasAnyRole("MODERATOR", "ADMIN")

                .mvcMatchers(HttpMethod.GET,"/api/files/**").hasAuthority("file_read")
                .mvcMatchers(HttpMethod.GET,"/api/files/base64").hasAuthority("file_read")
                .mvcMatchers(HttpMethod.DELETE,"/api/files/").hasAnyRole("MODERATOR", "ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/api/files/fileName/**").hasAnyRole("EDITOR", "ADMIN", "MODERATOR")

                .mvcMatchers(HttpMethod.GET,"/api/jobs/**").hasAuthority("job_read")
                .mvcMatchers(HttpMethod.POST,"/api/jobs/**").hasAuthority("job_edit")
                .mvcMatchers(HttpMethod.PUT,"/api/jobs/**").hasAuthority("job_edit")
                .mvcMatchers(HttpMethod.DELETE,"/api/jobs/**").hasAuthority("job_delete")

                .mvcMatchers(HttpMethod.POST,"/user/roles/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT,"/user/roles/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/user/roles/**").hasRole("ADMIN")

                .mvcMatchers(HttpMethod.POST,"/user/privileges/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT,"/user/privileges/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/user/privileges/**").hasRole("ADMIN")

                .mvcMatchers(HttpMethod.GET,"/api/fileMetadata/orchestra").hasAnyRole("JBB_MEMBER","ORCH_MEMBER")
                .mvcMatchers(HttpMethod.GET,"/api/fileMetadata/band").hasRole("JBB_MEMBER")
                .mvcMatchers(HttpMethod.GET,"/api/fileMetadata").hasAnyRole("EDITOR", "ADMIN", "MODERATOR")
                .mvcMatchers(HttpMethod.GET,"/api/fileMetadata/instrument/orchestra").hasAnyRole("JBB_MEMBER","ORCH_MEMBER")
                .mvcMatchers(HttpMethod.GET,"/api/fileMetadata/instrument/band").hasRole("JBB_MEMBER")
                .mvcMatchers(HttpMethod.GET,"/api/fileMetadata/instrument").hasAnyRole("EDITOR", "ADMIN", "MODERATOR")

                .mvcMatchers("/api/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler()).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Methods", "Access-Control-Allow-Headers", "Access-Control-Max-Age",
                "Access-Control-Request-Headers", "Access-Control-Request-Method"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}
