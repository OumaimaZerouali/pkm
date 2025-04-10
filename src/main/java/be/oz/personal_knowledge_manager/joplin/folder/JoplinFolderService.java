package be.oz.personal_knowledge_manager.joplin.folder;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

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
                .build();
    }

    @Override
    public List<Folder> getFolders() {
        var url = "http://localhost:41184/folders?token=1344d323c95adec4abc5425fc0e25d32bb8516f1a17df391a499d44247da39dcff650f479a238195d6f6ec8e3bbeb92e73c78c83cd6cef3543c58d94c83981a4";
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

