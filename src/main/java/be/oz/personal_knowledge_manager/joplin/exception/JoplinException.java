package be.oz.personal_knowledge_manager.joplin.exception;

public class JoplinException extends RuntimeException {
    public JoplinException(String message) {
        super(message);
    }

    public JoplinException(String message, Throwable cause) {
        super(message, cause);
    }
}
