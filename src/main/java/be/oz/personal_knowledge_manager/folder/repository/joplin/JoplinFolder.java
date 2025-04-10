package be.oz.personal_knowledge_manager.folder.repository.joplin;

public record JoplinFolder(
        String id,
        String title,
        String parent_id,
        int created_time,
        int updated_time,
        String icon
) {
}
