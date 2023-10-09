package com.jkeez.boatback.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.Arrays;

/**
 * Spring security configuration class
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Password encryption bean
     * @return password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS configuration's bean
     * @return source configuration
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type", "Access-Control-Allow-Origin"));
        configuration.setExposedHeaders(Arrays.asList("X-Get-Header", "Access-Control-Allow-Origin"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Bean for authorizing requests.
     * @param http http
     * @return security filter chain
     * @throws Exception e
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO add spring security authorization
        http.authorizeHttpRequests((authz) -> authz
                .anyRequest().permitAll()
        ).httpBasic(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

