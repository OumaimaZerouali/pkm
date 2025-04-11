package be.oz.personal_knowledge_manager.ai.exception;

public class AIException extends RuntimeException {
    public AIException(String message) {
        super(message);
    }

    public AIException(String message, Throwable cause) {
        super(message, cause);
    }
}
