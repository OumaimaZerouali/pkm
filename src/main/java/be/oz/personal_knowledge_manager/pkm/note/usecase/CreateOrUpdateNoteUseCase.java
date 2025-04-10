package be.oz.personal_knowledge_manager.pkm.note.usecase;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrUpdateNoteUseCase {
    private final NoteRepository repository;

    public Note execute(Note note) {
        var existingNote = repository.getNoteById(note.getId());

        if (existingNote != null) {
            return repository.updateNoteById(note, note.getId());
        }

        return repository.createNote(note);
    }
}
