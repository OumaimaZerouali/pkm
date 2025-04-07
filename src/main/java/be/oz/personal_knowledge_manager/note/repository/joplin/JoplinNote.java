package be.oz.personal_knowledge_manager.note.repository.joplin;

public record JoplinNote(
        String id,
        String title,
        String parent_id,
        long deleted_time
) {}
