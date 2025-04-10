package be.oz.personal_knowledge_manager.pkm.note.usecase;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateNoteByIdUseCase {
    private final NoteRepository repository;

    public Note execute(Note note, String id) {
        return repository.updateNoteById(note, note.getId());
    }
}
