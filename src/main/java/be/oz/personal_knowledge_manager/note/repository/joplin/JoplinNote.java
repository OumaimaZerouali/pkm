package be.oz.personal_knowledge_manager.note.repository.joplin;

public record JoplinNote(
        String id,
        String parent_id,
        String title,
        String body,
        String body_html,
        int created_time,
        int updated_time,
        String author,
        String source_url,
        int is_todo,
        int todo_completed,
        int todo_due,
        String source
) {}
