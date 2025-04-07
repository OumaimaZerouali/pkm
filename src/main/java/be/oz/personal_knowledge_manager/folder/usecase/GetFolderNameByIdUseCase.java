package be.oz.personal_knowledge_manager.folder.usecase;

import be.oz.personal_knowledge_manager.folder.domain.Folder;
import be.oz.personal_knowledge_manager.folder.repository.FolderRepository;
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
