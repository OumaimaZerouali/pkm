package be.oz.personal_knowledge_manager.note.repository.joplin;

import be.oz.personal_knowledge_manager.note.domain.Note;
import be.oz.personal_knowledge_manager.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JoplinService implements NoteRepository {
    private final String joplinUrl;
    private final String joplinToken;
    private final RestTemplate restTemplate;

    public JoplinService(RestTemplate restTemplate,
                         @Value("${joplin.token}") String token,
                         @Value("${joplin.url}") String joplinUrl) {
        this.restTemplate = restTemplate;
        this.joplinToken = token;
        this.joplinUrl = joplinUrl;
    }

    @Override
    public List<Note> findAllNotes() {
        var url = joplinUrl + "?token=" + joplinToken;
        var response = restTemplate.getForObject(url, JoplinNotes.class);

        if (response == null || response.items() == null) {
            return Collections.emptyList();
        }

        return response.items().stream()
                .map(item -> Note.builder()
                        .id(item.id())
                        .title(item.title())
                        .build())
                .toList();
    }

    @Override
    public Note createNote(Note note) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", note.getTitle());
        requestBody.put("body", note.getContent());
        requestBody.put("folders", note.getFolder());

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                joplinUrl + "?token=" + joplinToken,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                Map.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.err.println("Error response from Joplin: " + responseEntity.getBody());
        }

        Map<String, Object> responseBody = responseEntity.getBody();
        if (responseBody != null) {
            var id = (String) responseBody.get("id");
            var title = (String) responseBody.get("title");
            var body = (String) responseBody.get("body");
            var folder = (String) responseBody.get("folders");

            return Note.builder()
                    .id(id)
                    .title(title)
                    .content(body)
                    .folder(folder)
                    .created(LocalDateTime.now())
                    .updated(LocalDateTime.now())
                    .build();
        } else {
            throw new RuntimeException("Error creating note: No response from Joplin API");
        }
    }
}

