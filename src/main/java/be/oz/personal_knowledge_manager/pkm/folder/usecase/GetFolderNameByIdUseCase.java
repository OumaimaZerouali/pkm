package be.oz.personal_knowledge_manager.pkm.folder.usecase;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFolderNameByIdUseCase {
    private final FolderRepository repository;

    public Folder execute(String id) {
        return repository.getFolderById(id);
    }
}
