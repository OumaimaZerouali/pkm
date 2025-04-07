package be.oz.personal_knowledge_manager.note.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NoteConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
