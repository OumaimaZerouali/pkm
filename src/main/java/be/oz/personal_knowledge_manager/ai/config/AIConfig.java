package be.oz.personal_knowledge_manager.ai.config;

import be.oz.personal_knowledge_manager.ai.domain.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AIConfig {

    @Value("${spring.ai.ollama.chat.options.model2}")
    private String model_gemma;

    @Value("${spring.ai.ollama.chat.options.model}")
    private String model_llama;

    @Value("${spring.ai.ollama.chat.options.model3}")
    private String model_mistral;

    @Value("${spring.ai.ollama.chat.options.model5}")
    private String model_deepseek;

    @Value("${spring.ai.ollama.chat.options.model4}")
    private String model_codellama;

    @Bean
    public Map<Model, String> models() {
        Map<Model, String> models = new HashMap<>();
        models.put(Model.GEMMA, model_gemma);
        models.put(Model.LLAMA3, model_llama);
        models.put(Model.MISTRAL, model_mistral);
        models.put(Model.DEEPSEEK, model_deepseek);
        models.put(Model.CODELLAMA, model_codellama);
        return models;
    }
}
