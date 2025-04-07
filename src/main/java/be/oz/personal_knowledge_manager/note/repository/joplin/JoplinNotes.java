package be.oz.personal_knowledge_manager.note.repository.joplin;

import java.util.List;

public record JoplinNotes(
        List<JoplinNote> items,
        boolean has_more
) {}
