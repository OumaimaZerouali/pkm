package be.oz.personal_knowledge_manager.pkm.folder.usecase;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFolderNameByIdUseCaseTest {

    @Mock
    private FolderRepository repository;

    @InjectMocks
    private GetFolderNameByIdUseCase useCase;

    @Test
    void givenId_whenExecute_thenReturnsFolder() {
        var folder = Folder.builder()
                .id("b0a45b271cde4528ade82b40f3643771")
                .name("Generated Notes")
                .icon("")
                .build();
        when(repository.getFolderById("b0a45b271cde4528ade82b40f3643771")).thenReturn(folder);

        var result = useCase.execute("b0a45b271cde4528ade82b40f3643771");

        assertThat(result).isEqualTo(folder);
        verify(repository).getFolderById("b0a45b271cde4528ade82b40f3643771");
    }

    @Test
    void givenFaultyId_whenExecute_thenReturnsNull() {
        when(repository.getFolderById("unknown")).thenReturn(null);

        var result = useCase.execute("unknown");

        assertThat(result).isNull();
        verify(repository).getFolderById("unknown");
    }
}