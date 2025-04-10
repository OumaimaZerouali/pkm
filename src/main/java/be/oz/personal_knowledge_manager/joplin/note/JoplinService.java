package be.oz.personal_knowledge_manager.joplin.note;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;
import be.oz.personal_knowledge_manager.pkm.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        var url = joplinUrl + "/notes?token=" + joplinToken;
        var response = restTemplate.getForObject(url, JoplinNotes.class);

        if (response == null || response.items() == null) {
            return Collections.emptyList();
        }

        return response.items().stream()
                .map(item -> Note.builder()
                        .id(item.id())
                        .title(item.title())
                        .content(item.body())
                        .content_html(item.body_html())
                        .author(item.author())
                        .source_url(item.source_url())
                        .folder(item.parent_id())
                        .created(Instant.ofEpochSecond(item.created_time())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime())
                        .updated(Instant.ofEpochSecond(item.updated_time())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime())
                        .build())
                .toList();
    }

    @Override
    public Note getNoteById(String id) {
        var url = joplinUrl + "/notes/" + id + "?token=" + joplinToken + "&fields=id,parent_id,title,body,author,source_url";
        var response = restTemplate.getForObject(url, JoplinNote.class);

        if (response == null) {
            return null;
        }

        return Note.builder()
                .id(response.id())
                .title(response.title())
                .content(response.body())
                .content_html(response.body_html())
                .author(response.author())
                .source_url(response.source_url())
                .folder(response.parent_id())
                .created(Instant.ofEpochSecond(response.created_time())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .updated(Instant.ofEpochSecond(response.updated_time())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();
    }

    @Override
    public Note createNote(Note note) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", note.getTitle());
        requestBody.put("body", note.getContent());
        requestBody.put("folders", note.getFolder());

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                joplinUrl + "/notes?token=" + joplinToken,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                Map.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.err.println("Error response from Joplin: " + responseEntity.getBody());
        }

        var responseBody = responseEntity.getBody();
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

    @Override
    public Note updateNoteById(Note note, String id) {
        var url = joplinUrl + "/notes/" + id + "?token=" + joplinToken;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", note.getTitle());
        requestBody.put("body", note.getContent());
        requestBody.put("parent_id", note.getFolder());
        requestBody.put("author", note.getAuthor());
        requestBody.put("source_url", note.getSource_url());

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(requestBody),
                Map.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to update note in Joplin");
        }

        var body = response.getBody();
        return Note.builder()
                .id((String) body.get("id"))
                .title((String) body.get("title"))
                .content((String) body.get("body"))
                .folder((String) body.get("parent_id"))
                .author((String) body.get("author"))
                .source_url((String) body.get("source_url"))
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
    }

    @Override
    public void deleteNoteById(String id) {
        var url = joplinUrl + "/notes/" + id + "?token=" + joplinToken + "&permanent=1";
        restTemplate.delete(url);
    }
}

