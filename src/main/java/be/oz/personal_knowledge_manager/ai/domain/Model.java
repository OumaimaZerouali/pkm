package be.oz.personal_knowledge_manager.ai.domain;

import lombok.Getter;

@Getter
public enum Model {
    LLAMA3("llama3:latest"),
    GEMMA("gemma:latest"),
    MISTRAL("mistral:latest"),
    CODELLAMA("codellama:latest"),
    DEEPSEEK("deepseek-r1:latest");

    private final String model;

    Model(String model) {
        this.model = model;
    }
}
