package be.oz.personal_knowledge_manager.ai.repository;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.usecase.CreateOrUpdateNoteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;

@Slf4j
@RequiredArgsConstructor
public class NoteTools {
    private final CreateOrUpdateNoteUseCase createOrUpdateNoteUseCase;

    @Tool
    public String createNote(String content, String folder) {
        var note = Note.builder()
                .title("AI Note: " + content.substring(0, Math.min(30, content.length())))
                .author("AI Assistant")
                .content(content)
                .folder(folder)
                .build();

        var saved = createOrUpdateNoteUseCase.execute(note);
        log.info("Created note: {}", saved.getId());
        return "Note created with ID: " + saved.getId();
    }
}
