package be.oz.personal_knowledge_manager.pkm.note.repository;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.json.JsonCompareMode;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoteControllerTest extends AbstractControllerTest {

    @Nested
    class GetAllNotes {

        @Test
        @WithMockUser(username = "pkm", roles = "USER")
        void givenNotes_whenGetNotes_thenReturnNotes() throws Exception {
            var notes = List.of(
                    Note.builder()
                            .id("7682df50eb1b4faab07c68a095fceb25")
                            .title("Generated Note: Spongebob SquarePants")
                            .author("Ollama3")
                            .content("Spongebob SquarePants")
                            .folder("7682df50eb1b4faab07c68a095fceb26")
                            .build(),
                    Note.builder()
                            .id("7682df50eb1b4faab07c68a095fceb24")
                            .title("Generated Note: Patrick Star")
                            .author("Ollama3")
                            .content("Patrick Star")
                            .folder("7682df50eb1b4faab07c68a095fceb23")
                            .build()
            );

            when(getNotesUseCase.execute()).thenReturn(notes);

            mockMvc.perform(get("/notes"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{\"id\":\"7682df50eb1b4faab07c68a095fceb25\",\"title\":\"Generated Note: Spongebob SquarePants\",\"content\":\"Spongebob SquarePants\",\"content-html\":null,\"author\":\"Ollama3\",\"source_url\":null,\"folder\":\"7682df50eb1b4faab07c68a095fceb26\",\"created\":null,\"updated\":null},{\"id\":\"7682df50eb1b4faab07c68a095fceb24\",\"title\":\"Generated Note: Patrick Star\",\"content\":\"Patrick Star\",\"content-html\":null,\"author\":\"Ollama3\",\"source_url\":null,\"folder\":\"7682df50eb1b4faab07c68a095fceb23\",\"created\":null,\"updated\":null}]", JsonCompareMode.STRICT));
        }
    }

    @Nested
    class GetNoteById {

        @Test
        @WithMockUser(username = "pkm", roles = "USER")
        void givenNote_whenGetNoteById_thenReturnNote() throws Exception {
            var note =  Note.builder()
                    .id("7682df50eb1b4faab07c68a095fceb25")
                    .title("Generated Note: Spongebob SquarePants")
                    .author("Ollama3")
                    .content("Spongebob SquarePants")
                    .folder("7682df50eb1b4faab07c68a095fceb26")
                    .build();

            when(getNoteByIdUseCase.execute("7682df50eb1b4faab07c68a095fceb25")).thenReturn(note);

            mockMvc.perform(get("/notes/{id}", "7682df50eb1b4faab07c68a095fceb25"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"id\":\"7682df50eb1b4faab07c68a095fceb25\",\"title\":\"Generated Note: Spongebob SquarePants\",\"content\":\"Spongebob SquarePants\",\"content-html\":null,\"author\":\"Ollama3\",\"source_url\":null,\"folder\":\"7682df50eb1b4faab07c68a095fceb26\",\"created\":null,\"updated\":null}", JsonCompareMode.STRICT));
        }
    }

    @Nested
    class CreateOrUpdateNote {
        @Test
        @WithMockUser(username = "pkm", roles = "USER")
        void givenNewNote_whenCreateOrUpdateNote_thenReturnCreatedNote() throws Exception {
            var note = Note.builder()
                    .id("7682df50eb1b4faab07c68a095fceb25")
                    .title("New Note")
                    .folder("folder")
                    .content("content")
                    .content_html("contentHtml")
                    .source_url("url")
                    .author("author")
                    .created(LocalDateTime.of(2020, 1, 1, 0, 0))
                    .updated(LocalDateTime.of(2020, 1, 1, 0, 0))
                    .build();
            when(createOrUpdateNoteUseCase.execute(any(Note.class))).thenReturn(note);

            mockMvc.perform(put("/notes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf())
                            .content("{\"id\":\"7682df50eb1b4faab07c68a095fceb25\",\"title\":\"New Note\",\"folder\":\"folder\",\"content\":\"content\",\"contentHtml\":\"contentHtml\",\"sourceUrl\":\"url\",\"author\":\"author\"}"))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class DeleteNote {
        @Test
        @WithMockUser(username = "pkm", roles = "USER")
        void givenNoteToDelete_whenDeleteNoteById_thenReturnStatusOk() throws Exception {
            doNothing().when(deleteNoteByIdUseCase).execute("7682df50eb1b4faab07c68a095fceb25");

            mockMvc.perform(delete("/notes/{id}", "7682df50eb1b4faab07c68a095fceb25")
                            .with(csrf()))
                    .andExpect(status().isOk());
        }
    }
}