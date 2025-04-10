package be.oz.personal_knowledge_manager.pkm.note.usecase;

import be.oz.personal_knowledge_manager.pkm.note.repository.NoteRepository;
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
