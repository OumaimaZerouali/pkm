package be.oz.personal_knowledge_manager.joplin.folder;

import be.oz.personal_knowledge_manager.joplin.exception.JoplinException;
import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FolderService implements FolderRepository {
    private final String joplinUrl;
    private final String joplinToken;
    private final RestTemplate restTemplate;

    public FolderService(RestTemplate restTemplate,
                         @Value("${joplin.token}") String token,
                         @Value("${joplin.url}") String joplinUrl) {
        this.restTemplate = restTemplate;
        this.joplinToken = token;
        this.joplinUrl = joplinUrl;
    }

    @Override
    public Optional<Folder> getFolderById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            var url = joplinUrl + "/folders/" + id + "?token=" + joplinToken;
            var response = restTemplate.getForObject(url, JoplinFolder.class);

            if (response == null) {
                return Optional.empty();
            }

            return Optional.of(Folder.builder()
                    .id(response.id())
                    .name(response.title())
                    .build());

        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Folder> getFolders() {
        var url = joplinUrl + "/folders?token=" + joplinToken;
        var response = restTemplate.getForObject(url, JoplinFolders.class);

        if (response == null || response.items() == null) {
            return Collections.emptyList();
        }

        return response.items().stream().map(
                folder -> Folder.builder()
                        .name(folder.title())
                        .id(folder.id())
                        .build()
        ).toList();
    }

    @Override
    public void deleteFolderById(String id) {
        var url = joplinUrl + "/folders/" + id + "?token=" + joplinToken + "&permanent=1";
        restTemplate.delete(url);
    }

    @Override
    public Folder createFolder(Folder folder) {
        var url = joplinUrl + "/folders?token=" + joplinToken;

        Map<String, String> request = new HashMap<>();
        request.put("title", folder.getName());

        var response = restTemplate.postForObject(url, request, JoplinFolder.class);

        if (response == null) {
            throw new JoplinException("Failed to create folder");
        }

        return Folder.builder()
                .id(response.id())
                .name(response.title())
                .build();
    }

    @Override
    public String getFolderIdByName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        return getFolders().stream()
                .filter(folder -> folder.getName().equalsIgnoreCase(name.trim()))
                .map(Folder::getId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Folder updateFolderById(Folder folder, String id) {
        var url = joplinUrl + "/folders/" + id + "?token=" + joplinToken;

        Map<String, String> request = new HashMap<>();
        request.put("title", folder.getName());

        var entity = new HttpEntity<>(request);

        var response = restTemplate.exchange(url, HttpMethod.PUT, entity, JoplinFolder.class);

        if (response.getBody() == null) {
            throw new JoplinException("Failed to update folder with ID: " + id);
        }

        return Folder.builder()
                .id(response.getBody().id())
                .name(response.getBody().title())
                .build();
    }
}

