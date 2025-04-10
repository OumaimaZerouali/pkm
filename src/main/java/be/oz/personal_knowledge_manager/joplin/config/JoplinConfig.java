package be.oz.personal_knowledge_manager.joplin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JoplinConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
