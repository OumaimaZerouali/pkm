package be.oz.personal_knowledge_manager.joplin.note;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteServiceTest {
    @Autowired
    private NoteService service;

    @Test
    void givenNotes_whenGetNotes_thenReturnNotes() {
        var notes = service.findAllNotes();

        assertThat(notes)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void givenNoteId_whenGetNoteById_thenReturnNote() {
        var noteId = "7682df50eb1b4faab07c68a095fceb25";
        var note = service.getNoteById(noteId);

        assertThat(note).isNotNull();
    }

    @Test
    void givenNote_whenCreateOrUpdateNote_thenReturnNote() {
        var newNote = Note.builder()
                .id("b0a45b271cde4528ade82b40f3643770")
                .title("Generated Note: Spongebob SquarePants")
                .content("Meet SpongeBob SquarePants, the optimistic and enthusiastic sea sponge who lives in a pineapple under the sea. " +
                        "This beloved Nickelodeon character has been delighting audiences of all ages with his goofy antics and lovable personality." +
                        "Created by marine biologist-turned-cartoonist **Stephen Hillenburg**, SpongeBob premiered on May 1, 1999.")
                .author("ollama3")
                .folder("2ba76133c0474d45b3cf02dbfbd896ca")
                .source_url("https://github.com/oz/oz-personal-knowledge-manager")
                .build();

        var created = service.createNote(newNote);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotBlank();
        assertThat(created.getTitle()).isEqualTo("Generated Note: Spongebob SquarePants");

        created.setTitle("Updated Title: Spongebob SquarePants & Patrick Star");
        created.setContent("Updated Content");
        created.setFolder(newNote.getFolder());
        created.setAuthor(newNote.getAuthor());
        created.setSource_url(newNote.getSource_url());

        var updated = service.updateNoteById(created, created.getId());
        assertThat(updated.getTitle()).isEqualTo("Updated Title: Spongebob SquarePants & Patrick Star");

        service.deleteNoteById(updated.getId());
    }
}