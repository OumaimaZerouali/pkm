package be.oz.personal_knowledge_manager.pkm.note.usecase;

import be.oz.personal_knowledge_manager.pkm.note.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class DeleteNoteByIdUseCaseTest {

    @Mock
    private NoteRepository repository;

    @InjectMocks
    private DeleteNoteByIdUseCase useCase;

    @Test
    void givenValidId_whenExecute_thenRepositoryDeleteCalled() {
        var noteId = "7682df50eb1b4faab07c68a095fceb25";

        useCase.execute(noteId);

        verify(repository).deleteNoteById(noteId);
    }

    @Test
    void givenRepositoryThrowsException_whenExecute_thenExceptionPropagated() {
        var faultyId = "fail-id";
        doThrow(new RuntimeException("Deletion failed")).when(repository).deleteNoteById(faultyId);

        assertThrows(RuntimeException.class, () -> useCase.execute(faultyId));
        verify(repository).deleteNoteById(faultyId);
    }
}