package be.oz.personal_knowledge_manager.pkm.folder.usecase;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFoldersUseCase {
    private final FolderRepository repository;

    public List<Folder> execute(){
        return repository.getFolders();
    }
}
