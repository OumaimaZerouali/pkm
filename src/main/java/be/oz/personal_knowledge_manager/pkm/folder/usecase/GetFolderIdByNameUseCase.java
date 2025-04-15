package be.oz.personal_knowledge_manager.pkm.folder.usecase;

import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFolderIdByNameUseCase {
    private final FolderRepository repository;

    public String execute(String name) {
        return repository.getFolderIdByName(name);
    }
}
