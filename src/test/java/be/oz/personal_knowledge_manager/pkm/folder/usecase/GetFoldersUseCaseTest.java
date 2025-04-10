package be.oz.personal_knowledge_manager.pkm.folder.usecase;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
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
class GetFoldersUseCaseTest {

    @Mock
    private FolderRepository repository;

    @InjectMocks
    private GetFoldersUseCase useCase;

    @Test
    void givenFolders_whenExecute_thenReturnsListOfFolders() {
        var folder1 = Folder.builder()
                .id("b0a45b271cde4528ade82b40f3643770")
                .name("General Notes")
                .icon("")
                .build();
        var folder2 = Folder.builder()
                .id("b0a45b271cde4528ade82b40f3643771")
                .name("Generated Notes")
                .icon("")
                .build();
        when(repository.getFolders()).thenReturn(List.of(folder1, folder2));

        var result = useCase.execute();

        assertThat(result)
                .hasSize(2)
                .contains(folder1, folder2);
        verify(repository).getFolders();
    }

    @Test
    void givenNoFolders_whenExecute_thenReturnEmptyList() {
        when(repository.getFolders()).thenReturn(Collections.emptyList());

        var result = useCase.execute();

        assertThat(result).isEmpty();
        verify(repository).getFolders();
    }
}