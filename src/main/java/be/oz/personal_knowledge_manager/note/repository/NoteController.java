package be.oz.personal_knowledge_manager.note.repository;

import be.oz.personal_knowledge_manager.note.domain.Note;
import be.oz.personal_knowledge_manager.note.usecase.CreateNoteUseCase;
import be.oz.personal_knowledge_manager.note.usecase.GetNoteByIdUseCase;
import be.oz.personal_knowledge_manager.note.usecase.GetNotesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final GetNotesUseCase getNotesUseCase;
    private final CreateNoteUseCase createNoteUseCase;
    private final GetNoteByIdUseCase getNoteByIdUseCase;

    @GetMapping
    public List<Note> getNotes() {
        return getNotesUseCase.execute();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable String id) {
        return getNoteByIdUseCase.execute(id);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        var createdNote = createNoteUseCase.execute(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }
}
