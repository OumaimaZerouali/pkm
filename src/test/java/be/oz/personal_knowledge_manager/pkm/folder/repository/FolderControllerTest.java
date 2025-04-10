package be.oz.personal_knowledge_manager.pkm.folder.repository;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.json.JsonCompareMode;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FolderControllerTest extends AbstractControllerTest {
    @Test
    @WithMockUser(username = "pkm", roles = "USER")
    void givenFolders_whenGetFolders_thenReturnListOfFolders() throws Exception {
        var folder1 = Folder.builder()
                .id("b0a45b271cde4528ade82b40f3643771")
                .name("Generated Notes")
                .icon("")
                .build();
        var folder2 = Folder.builder()
                .id("b0a45b271cde4528ade82b40f3643776")
                .name("General")
                .icon("")
                .build();

        when(getFoldersUseCase.execute()).thenReturn(List.of(folder1, folder2));

        mockMvc.perform(get("/folders"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "pkm", roles = "USER")
    void givenValidId_whenGetFolderById_thenReturnFolder() throws Exception {
        var folder = Folder.builder()
                .id("b0a45b271cde4528ade82b40f3643776")
                .name("General")
                .icon("")
                .build();

        when(getFolderNameByIdUseCase.execute("b0a45b271cde4528ade82b40f3643776")).thenReturn(folder);

        mockMvc.perform(get("/folders/{id}", "b0a45b271cde4528ade82b40f3643776"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"b0a45b271cde4528ade82b40f3643776\",\"name\":\"General\",\"icon\":\"\"}", JsonCompareMode.STRICT));
    }

    @Test
    @WithMockUser(username = "pkm", roles = "USER")
    void givenFaultyId_whenGetFolderById_thenReturnNotFound() throws Exception {
        when(getFolderNameByIdUseCase.execute("unknown")).thenReturn(null);

        mockMvc.perform(get("/folders/unknown"))
                .andExpect(status().isNotFound());
    }
}