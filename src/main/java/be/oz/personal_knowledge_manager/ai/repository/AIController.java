package be.oz.personal_knowledge_manager.ai.repository;

import be.oz.personal_knowledge_manager.api.AiApi;
import be.oz.personal_knowledge_manager.folder.usecase.GetFolderNameByIdUseCase;
import be.oz.personal_knowledge_manager.model.AIRequestDTO;
import be.oz.personal_knowledge_manager.model.ChatWithAI200ResponseDTO;
import be.oz.personal_knowledge_manager.model.ChatWithAIRequestDTO;
import be.oz.personal_knowledge_manager.model.NoteDTO;
import be.oz.personal_knowledge_manager.note.domain.Note;
import be.oz.personal_knowledge_manager.note.usecase.CreateNoteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AIController implements AiApi {
    private final ChatModel chatModel;
    private final CreateNoteUseCase createNoteUseCase;
    private final GetFolderNameByIdUseCase getFolderNameByIdUseCase;

    @Override
    public ResponseEntity<ChatWithAI200ResponseDTO> chatWithAI(ChatWithAIRequestDTO chatWithAIRequestDTO) {
        var userMessage = chatWithAIRequestDTO.getMessage();
        var aiResponse = chatModel.call(userMessage);

        var responseDTO = new ChatWithAI200ResponseDTO(aiResponse);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<NoteDTO> createNoteFromAI(AIRequestDTO aiRequestDTO) {
        var userMessage = aiRequestDTO.getMessage();
        var folderId = aiRequestDTO.getFolder();

        var aiResponse = chatModel.call(userMessage);

        var folder = getFolderNameByIdUseCase.execute(folderId);
        if (folder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        var title = generateTitle(aiResponse);

        var newNote = Note.builder()
                .title("Generated Note: " + title)
                .author("AI Assistant")
                .content(aiResponse)
                .folder(folder.getName())
                .build();

        var savedNote = createNoteUseCase.execute(newNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(NoteDTO.builder()
                .id(savedNote.getId())
                .title(savedNote.getTitle())
                .folder(savedNote.getFolder())
                .created(savedNote.getCreated())
                .updated(savedNote.getUpdated())
                .author(savedNote.getAuthor())
                .content(savedNote.getContent())
                .contentHtml(savedNote.getContent_html())
                .sourceUrl(savedNote.getSource_url())
                .build());
    }

    private String generateTitle(String aiResponse) {
        aiResponse = aiResponse.trim();

        var sentences = aiResponse.split("(?<=[.!?])\\s*");
        var title = sentences.length > 0 ? sentences[0] : aiResponse;

        if (title.length() > 50) {
            title = title.substring(0, 50) + "...";
        }

        return title;
    }
}
