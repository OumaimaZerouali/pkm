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

        http.securityMatcher("/api/**")
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/notes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/notes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/notes/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/folders/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/prompt/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/prompt/*").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}

