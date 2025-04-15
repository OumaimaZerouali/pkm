package be.oz.personal_knowledge_manager.joplin.folder;

public record JoplinFolder(
        String id,
        String title,
        String parent_id,
        long created_time,
        long updated_time,
        String icon
) {
}
