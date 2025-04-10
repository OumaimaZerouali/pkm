package be.oz.personal_knowledge_manager.pkm.note.repository;

import be.oz.personal_knowledge_manager.api.NotesApi;
import be.oz.personal_knowledge_manager.model.NoteDTO;
import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.usecase.CreateOrUpdateNoteUseCase;
import be.oz.personal_knowledge_manager.pkm.note.usecase.DeleteNoteByIdUseCase;
import be.oz.personal_knowledge_manager.pkm.note.usecase.GetNoteByIdUseCase;
import be.oz.personal_knowledge_manager.pkm.note.usecase.GetNotesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteController implements NotesApi {
    private final GetNotesUseCase getNotesUseCase;
    private final GetNoteByIdUseCase getNoteByIdUseCase;
    private final DeleteNoteByIdUseCase deleteNoteByIdUseCase;
    private final CreateOrUpdateNoteUseCase createOrUpdateNoteUseCase;

    @Override
    public ResponseEntity<NoteDTO> createOrUpdateNote(NoteDTO noteDTO) {
        var note = createOrUpdateNoteUseCase.execute(Note.builder()
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
        return ResponseEntity.ok(new NoteDTO(
                note.getId(),
                note.getTitle(),
                note.getFolder(),
                note.getContent_html(),
                note.getSource_url(),
                note.getAuthor(),
                note.getContent(),
                note.getCreated(),
                note.getUpdated()
        ));
    }

    @Override
    public ResponseEntity<Void> deleteNoteById(String id) {
        deleteNoteByIdUseCase.execute(id);
        return ResponseEntity.ok().build();
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
