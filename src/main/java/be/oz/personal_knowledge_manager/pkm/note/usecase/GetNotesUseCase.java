package be.oz.personal_knowledge_manager.pkm.note.usecase;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetNotesUseCase {
    private final NoteRepository repository;

    public List<Note> execute() {
        return repository.findAllNotes();
    }
}
