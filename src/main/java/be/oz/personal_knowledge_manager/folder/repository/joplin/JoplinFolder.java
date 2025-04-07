package be.oz.personal_knowledge_manager.folder.repository.joplin;

public record JoplinFolder(
        String id,
        String title,
        int created_time,
        int updated_time,
        String icon
) {
}
