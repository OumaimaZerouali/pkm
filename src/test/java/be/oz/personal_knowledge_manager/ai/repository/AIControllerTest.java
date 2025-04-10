package be.oz.personal_knowledge_manager.ai.repository;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.json.JsonCompareMode;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AIControllerTest extends AbstractControllerTest {

    @Test
    @WithMockUser(username = "pkm", roles = "USER")
    void givenMessage_whenChatWithAI_thenReturnAIResponse() throws Exception {
        var aiResponse = "Hello Human, how can I help you?";
        when(chatModel.call("Hello AI")).thenReturn(aiResponse);

        mockMvc.perform(post("/ai/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content("{\"message\":\"Hello AI\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "pkm", roles = "USER")
    void givenRequestToCreateNote_whenCreateNoteFromAI_thenReturnCreatedNote() throws Exception {
        var aiResponse = "Very long note about AI";
        var folderId = "b0a45b271cde4528ade82b40f3643774";
        var folderName = "General - Everything & Anything";

        when(chatModel.call("Can you create a note about AI")).thenReturn(aiResponse);
        when(getFolderNameByIdUseCase.execute(folderId)).thenReturn(Folder.builder()
                .id(folderId)
                .name(folderName)
                .build());
        when(createOrUpdateNoteUseCase.execute(any(Note.class))).thenReturn(Note.builder()
                .id("b0a45b271cde4528ade82b40f3643770")
                .title("Very long note about AI")
                .content(aiResponse)
                .author("ollama3")
                .folder("b0a45b271cde4528ade82b40f3643774")
                .build());

        mockMvc.perform(post("/ai/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content("{\"message\":\"Can you create a note about AI\",\"folder\":\"b0a45b271cde4528ade82b40f3643774\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":\"b0a45b271cde4528ade82b40f3643770\",\"title\":\"Very long note about AI\",\"content\":\"Very long note about AI\",\"content-html\":null,\"author\":\"ollama3\",\"source_url\":null,\"folder\":\"b0a45b271cde4528ade82b40f3643774\",\"created\":null,\"updated\":null}", JsonCompareMode.STRICT));
    }
}