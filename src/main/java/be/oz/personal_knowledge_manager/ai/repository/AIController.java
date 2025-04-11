package be.oz.personal_knowledge_manager.ai.repository;

import be.oz.personal_knowledge_manager.ai.domain.Model;
import be.oz.personal_knowledge_manager.ai.exception.AIException;
import be.oz.personal_knowledge_manager.api.AiApi;
import be.oz.personal_knowledge_manager.model.*;
import be.oz.personal_knowledge_manager.pkm.folder.usecase.GetFolderNameByIdUseCase;
import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.usecase.CreateOrUpdateNoteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AIController implements AiApi {

    private final ChatModel chatModel;
    private final Map<Model, String> models;
    private final CreateOrUpdateNoteUseCase createOrUpdateNoteUseCase;
    private final GetFolderNameByIdUseCase getFolderNameByIdUseCase;

    public static final Model DEFAULT_MODEL = Model.DEEPSEEK;

    @Override
    public ResponseEntity<ChatWithAI200ResponseDTO> chatWithAI(ChatRequestDTO chatRequestDTO) {
        var requestedModel = useRequestModel(chatRequestDTO.getModel());
        var options = ChatOptions.builder()
                .model(requestedModel)
                .build();

        var prompt = new Prompt(new UserMessage(chatRequestDTO.getMessage()), options);
        var chatResponse = chatModel.call(prompt);
        var result = chatResponse.getResult().getOutput().getText();

        var responseDTO = new ChatWithAI200ResponseDTO(result);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<NoteDTO> createNoteFromAI(NoteRequestDTO noteRequestDTO) {
        var folderId = noteRequestDTO.getFolder();
        var userMessage = noteRequestDTO.getMessage();

        var requestedModel = useRequestModel(noteRequestDTO.getModel());
        var options = ChatOptions.builder()
                .model(requestedModel)
                .build();

        var prompt = new Prompt(new UserMessage(userMessage), options);
        var aiResponse = chatModel.call(prompt).getResult().getOutput().getText();

        var folder = getFolderNameByIdUseCase.execute(folderId);
        if (folder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        var title = createTitleBasedOnAIResponse(aiResponse);

        var newNote = Note.builder()
                .title("Generated Note: " + title)
                .author("AI Assistant")
                .content(aiResponse)
                .folder(folder.getName())
                .build();

        var savedNote = createOrUpdateNoteUseCase.execute(newNote);

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

    private String useRequestModel(ModelDTO modelDTO) {
        if (modelDTO == null) {
            return models.get(DEFAULT_MODEL);
        }

        try {
            var model = Model.valueOf(modelDTO.name());
            return models.get(model);
        } catch (IllegalArgumentException ex) {
            throw new AIException("Invalid model name: " + modelDTO.name(), ex);
        }
    }

    private String createTitleBasedOnAIResponse(String aiResponse) {
        if (aiResponse == null || aiResponse.trim().isEmpty()) {
            return "Untitled";
        }

        var trimmedResponse = aiResponse.trim();
        var sentences = trimmedResponse.split("(?<=[.!?])\\s*");
        var title = sentences.length > 0 ? sentences[0] : trimmedResponse;

        if (title.length() > 50) {
            title = title.substring(0, 50) + "...";
        }

        return title;
    }
}
