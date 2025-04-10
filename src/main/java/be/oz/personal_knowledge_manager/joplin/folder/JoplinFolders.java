package be.oz.personal_knowledge_manager.joplin.folder;

import java.util.List;

public record JoplinFolders(
        List<JoplinFolder> items,
        boolean has_more
) {}
