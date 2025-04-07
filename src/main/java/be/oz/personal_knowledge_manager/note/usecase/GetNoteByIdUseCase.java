package be.oz.personal_knowledge_manager.note.usecase;

import be.oz.personal_knowledge_manager.note.domain.Note;
import be.oz.personal_knowledge_manager.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class GetNoteByIdUseCase {
    private final NoteRepository repository;

    public Note execute(String id) {
        return repository.getNoteById(id);
    }
}
