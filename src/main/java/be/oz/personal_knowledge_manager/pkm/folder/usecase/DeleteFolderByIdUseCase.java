package be.oz.personal_knowledge_manager.pkm.folder.usecase;

import be.oz.personal_knowledge_manager.pkm.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFolderByIdUseCase {
    private final FolderRepository folderRepository;

    public void execute(String id) {
        folderRepository.deleteFolderById(id);
    }
}
