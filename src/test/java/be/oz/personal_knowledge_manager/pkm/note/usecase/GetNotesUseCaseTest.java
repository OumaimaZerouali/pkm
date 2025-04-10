package be.oz.personal_knowledge_manager.pkm.note.usecase;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetNotesUseCaseTest {

    @Mock
    private NoteRepository repository;

    @InjectMocks
    private GetNotesUseCase useCase;

    @Test
    void givenRepositoryReturnsNotes_whenExecute_thenReturnListOfNotes() {
        var note1 = Note.builder()
                .id("7682df50eb1b4faab07c68a095fceb25")
                .title("Generated Note: Spongebob SquarePants")
                .author("Ollama3")
                .content("Spongebob SquarePants")
                .folder("7682df50eb1b4faab07c68a095fceb26")
                .build();
        var note2 = Note.builder()
                .id("7682df50eb1b4faab07c68a095fceb24")
                .title("Generated Note: Patrick Star")
                .author("Ollama3")
                .content("Patrick Star")
                .folder("7682df50eb1b4faab07c68a095fceb23")
                .build();
        when(repository.findAllNotes()).thenReturn(List.of(note1, note2));

        var result = useCase.execute();
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactly(note1, note2);

        verify(repository).findAllNotes();
    }

    @Test
    void givenRepositoryReturnsEmptyList_whenExecute_thenReturnEmptyList() {
        when(repository.findAllNotes()).thenReturn(Collections.emptyList());

        var result = useCase.execute();

        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(repository).findAllNotes();
    }
}