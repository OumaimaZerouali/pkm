package be.oz.personal_knowledge_manager.pkm.folder.usecase;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrUpdateFolderUseCase {
    private final FolderRepository repository;

    public Folder execute(Folder folder) {
        var existingFolder = repository.getFolderById(folder.getId());

        if (existingFolder == null) {
            return repository.updateFolderById(folder, folder.getId());
        }

        return repository.createFolder(folder);
    }
}
