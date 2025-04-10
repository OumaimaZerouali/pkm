package be.oz.personal_knowledge_manager.note.usecase;

import be.oz.personal_knowledge_manager.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteNoteByIdUseCase {
    private final NoteRepository repository;

    public void execute(String id) {
        repository.deleteNoteById(id);
    }
}
