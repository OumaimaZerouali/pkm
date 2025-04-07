package be.oz.personal_knowledge_manager.folder.repository.joplin;

import be.oz.personal_knowledge_manager.folder.domain.Folder;
import be.oz.personal_knowledge_manager.folder.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JoplinFolderService implements FolderRepository {
    private final String joplinUrl;
    private final String joplinToken;
    private final RestTemplate restTemplate;

    public JoplinFolderService(RestTemplate restTemplate,
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
            return null;
        }

        return Folder.builder()
                .id(response.id())
                .name(response.title())
                .icon(response.icon())
                .build();
    }
}

