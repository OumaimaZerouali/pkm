package be.oz.personal_knowledge_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http.cors(Customizer.withDefaults());

        http.securityMatcher("/**")
                .authorizeHttpRequests(requests -> requests
                        // Notes
                        .requestMatchers(HttpMethod.GET, "/notes").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/notes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/notes/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/notes/*").permitAll()

                        // Folders
                        .requestMatchers(HttpMethod.GET, "/folders").permitAll()
                        .requestMatchers(HttpMethod.GET, "/folders/*").permitAll()

                        // AI Endpoints
                        .requestMatchers(HttpMethod.POST, "/ai/chat").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ai/notes").permitAll()
                        .requestMatchers("/prompt/**").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}

