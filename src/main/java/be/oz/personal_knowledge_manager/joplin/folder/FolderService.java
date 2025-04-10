package be.oz.personal_knowledge_manager.joplin.folder;

import be.oz.personal_knowledge_manager.joplin.exception.JoplinException;
import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

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
    public Folder getFolderById(String id) {
        var url = joplinUrl + "/folders/" + id + "?token=" + joplinToken;
        var response = restTemplate.getForObject(url, JoplinFolder.class);

        if (response == null) {
            throw new JoplinException("Error getting folder: No response from Joplin API");
        }

        return Folder.builder()
                .id(response.id())
                .name(response.title())
                .build();
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
}

