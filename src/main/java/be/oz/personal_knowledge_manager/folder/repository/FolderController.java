package be.oz.personal_knowledge_manager.folder.repository;

import be.oz.personal_knowledge_manager.api.FoldersApi;
import be.oz.personal_knowledge_manager.folder.usecase.GetFolderNameByIdUseCase;
import be.oz.personal_knowledge_manager.folder.usecase.GetFoldersUseCase;
import be.oz.personal_knowledge_manager.model.FolderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FolderController implements FoldersApi {
    private final GetFoldersUseCase getFoldersUseCase;
    private final GetFolderNameByIdUseCase getFolderNameByIdUseCase;

    @Override
    public ResponseEntity<FolderDTO> getFolderById(String id) {
        var folder = getFolderNameByIdUseCase.execute(id);

        if (folder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(FolderDTO.builder()
                .id(folder.getId())
                .name(folder.getName())
                .icon(folder.getIcon())
                .build());
    }

    @Override
    public ResponseEntity<List<FolderDTO>> getFolders() {
        var folders = getFoldersUseCase.execute();
        return ResponseEntity.ok(folders.stream().map(
                folder -> FolderDTO
                        .builder()
                        .id(folder.getId())
                        .name(folder.getName())
                        .icon(folder.getIcon())
                        .build()
        ).toList());
    }
}
