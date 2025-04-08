package be.oz.personal_knowledge_manager.note.repository;

import be.oz.personal_knowledge_manager.api.NotesApi;
import be.oz.personal_knowledge_manager.model.NoteDTO;
import be.oz.personal_knowledge_manager.note.domain.Note;
import be.oz.personal_knowledge_manager.note.usecase.CreateNoteUseCase;
import be.oz.personal_knowledge_manager.note.usecase.GetNoteByIdUseCase;
import be.oz.personal_knowledge_manager.note.usecase.GetNotesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteController implements NotesApi {
    private final GetNotesUseCase getNotesUseCase;
    private final CreateNoteUseCase createNoteUseCase;
    private final GetNoteByIdUseCase getNoteByIdUseCase;

    @Override
    public ResponseEntity<NoteDTO> updateNoteById(String id, NoteDTO noteDTO) {
        return null;
    }

    @Override
    public ResponseEntity<NoteDTO> createNote(NoteDTO noteDTO) {
        var note = createNoteUseCase.execute(Note.builder()
                .id(noteDTO.getId())
                .title(noteDTO.getTitle())
                .folder(noteDTO.getFolder())
                .created(noteDTO.getCreated())
                .updated(noteDTO.getUpdated())
                .author(noteDTO.getAuthor())
                .content(noteDTO.getContent())
                .content_html(noteDTO.getContentHtml())
                .source_url(noteDTO.getSourceUrl())
                .build());

        var responseNoteDTO = new NoteDTO(
                note.getId(),
                note.getTitle(),
                note.getFolder(),
                note.getContent_html(),
                note.getSource_url(),
                note.getAuthor(),
                note.getContent(),
                note.getCreated(),
                note.getUpdated()
        );

        return ResponseEntity.ok(responseNoteDTO);
    }

    @Override
    public ResponseEntity<Void> deleteNoteById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<NoteDTO> getNoteById(String id) {
        var note = getNoteByIdUseCase.execute(id);
        return ResponseEntity.ok(NoteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .folder(note.getFolder())
                .created(note.getCreated())
                .updated(note.getUpdated())
                .author(note.getAuthor())
                .content(note.getContent())
                .contentHtml(note.getContent_html())
                .sourceUrl(note.getSource_url())
                .build());
    }

    @Override
    public ResponseEntity<List<NoteDTO>> getNotes() {
        var notes = getNotesUseCase.execute();
        return ResponseEntity.ok(notes.stream().map(
                note -> NoteDTO.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .folder(note.getFolder())
                        .created(note.getCreated())
                        .updated(note.getUpdated())
                        .author(note.getAuthor())
                        .content(note.getContent())
                        .contentHtml(note.getContent_html())
                        .sourceUrl(note.getSource_url())
                        .build()
        ).toList());
    }
}
