package be.oz.personal_knowledge_manager.folder.repository.joplin;

import java.util.List;

public record JoplinFolders(
        List<JoplinFolder> items,
        boolean has_more
) {}
