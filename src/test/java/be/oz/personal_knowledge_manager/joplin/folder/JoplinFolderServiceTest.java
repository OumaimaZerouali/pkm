package be.oz.personal_knowledge_manager.joplin.folder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JoplinFolderServiceTest {

    @Autowired
    private FolderService service;

    @Test
    void givenFolders_whenGetFolders_thenReturnFolders() {
        var folders = service.getFolders();

        assertThat(folders)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void givenFolderId_whenGetFolderById_thenReturnFolder() {
        var folderId = "2ba76133c0474d45b3cf02dbfbd896ca";

        var folder = service.getFolderById(folderId);

        assertThat(folder).isNotNull();
   }
}