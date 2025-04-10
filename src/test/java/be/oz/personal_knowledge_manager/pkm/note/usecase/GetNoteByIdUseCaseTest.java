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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetNoteByIdUseCaseTest {

    @Mock
    private NoteRepository repository;

    @InjectMocks
    private GetNoteByIdUseCase useCase;

    @Test
    void givenValidId_whenExecute_thenReturnNote() {
        var id = "7682df50eb1b4faab07c68a095fceb25";
        var expectedNote = Note.builder()
                .id(id)
                .title("Generated Note: Test Note")
                .content("Test Content")
                .author("Test Author")
                .folder("7682df50eb1b4faab07c68a095fceb26")
                .build();

        when(repository.getNoteById(id)).thenReturn(expectedNote);

        var result = useCase.execute(id);
        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedNote);
        verify(repository).getNoteById(id);
    }

    @Test
    void givenInvalidId_whenExecute_thenReturnNull() {
        var invalidId = "not-found-id";
        when(repository.getNoteById(invalidId)).thenReturn(null);

        var result = useCase.execute(invalidId);
        assertThat(result).isNull();
        verify(repository).getNoteById(invalidId);
    }

    @Test
    void givenNullReturnedFromRepository_whenExecute_thenThrowJoplinException() {
        var id = "missing-note";

        when(repository.getNoteById(id)).thenThrow(new JoplinException("Error getting note: No response from Joplin API"));

        var exception = assertThrows(JoplinException.class, () -> useCase.execute(id));
        assertThat(exception.getMessage()).isEqualTo("Error getting note: No response from Joplin API");
    }
}