package be.oz.personal_knowledge_manager.pkm.note.usecase;

import be.oz.personal_knowledge_manager.joplin.exception.JoplinException;
import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrUpdateNoteUseCaseTest {

    @Mock
    private NoteRepository repository;

    @InjectMocks
    private CreateOrUpdateNoteUseCase useCase;

    @Test
    void givenNote_whenExecute_thenReturnCreatedNote() {
        var note = Note.builder()
                .id(null)
                .title("Generated Note: Spongebob SquarePants")
                .content("Meet SpongeBob SquarePants, the optimistic and enthusiastic sea sponge who lives in a pineapple under the sea. " +
                        "This beloved Nickelodeon character has been delighting audiences of all ages with his goofy antics and lovable personality." +
                        "Created by marine biologist-turned-cartoonist **Stephen Hillenburg**, SpongeBob premiered on May 1, 1999.")
                .author("ollama3")
                .folder("b0a45b271cde4528ade82b40f3643774")
                .build();

        when(repository.createNote(note)).thenReturn(note);

        var result = useCase.execute(note);
        assertThat(result)
                .isNotNull()
                .isEqualTo(note);
    }

    @Test
    void givenExistingNote_whenExecute_thenUpdateNote() {
        var note = Note.builder()
                .id("b0a45b271cde4528ade82b40f3643770")
                .title("Generated Note: Spongebob SquarePants")
                .content("Meet SpongeBob SquarePants, the optimistic and enthusiastic sea sponge who lives in a pineapple under the sea. " +
                        "This beloved Nickelodeon character has been delighting audiences of all ages with his goofy antics and lovable personality." +
                        "Created by marine biologist-turned-cartoonist **Stephen Hillenburg**, SpongeBob premiered on May 1, 1999.")
                .author("ollama3")
                .folder("b0a45b271cde4528ade82b40f3643774")
                .build();

        when(repository.getNoteById("b0a45b271cde4528ade82b40f3643770")).thenReturn(note);
        when(repository.updateNoteById(note, note.getId())).thenReturn(note);

        var result = useCase.execute(note);

        assertThat(result)
                .isNotNull()
                .isEqualTo(note);
        verify(repository).updateNoteById(note, note.getId());
        verify(repository, never()).createNote(any());
    }

    @Test
    void givenFaultyNote_whenExecute_thenThrowJoplinException() {
        var note = Note.builder()
                .title("fail")
                .content("fail")
                .folder("fail")
                .build();

        when(repository.createNote(note))
                .thenThrow(new JoplinException("Joplin failed"));

        var exception = assertThrows(JoplinException.class, () -> useCase.execute(note));
        assertThat(exception.getMessage()).isEqualTo("Joplin failed");
    }

    @Test
    void givenExistingNote_whenExecute_thenThrowException() {
        var note = Note.builder()
                .id("existing-id")
                .title("Oops")
                .content("Update fail")
                .folder("fail-folder")
                .build();

        when(repository.getNoteById("existing-id"))
                .thenReturn(note);
        when(repository.updateNoteById(note, note.getId()))
                .thenThrow(new JoplinException("Update failed"));

        var exception = assertThrows(JoplinException.class, () -> useCase.execute(note));
        assertThat(exception.getMessage()).isEqualTo("Update failed");
    }
}