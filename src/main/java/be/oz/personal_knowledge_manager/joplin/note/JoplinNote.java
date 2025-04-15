package be.oz.personal_knowledge_manager.joplin.note;

public record JoplinNote(
        String id,
        String parent_id,
        String title,
        String body,
        String body_html,
        long created_time,
        long updated_time,
        String author,
        String source_url,
        int is_todo,
        int todo_completed,
        int todo_due,
        String source
) {}
