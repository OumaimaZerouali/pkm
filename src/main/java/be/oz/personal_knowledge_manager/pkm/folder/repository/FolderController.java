package be.oz.personal_knowledge_manager.pkm.folder.repository;

import be.oz.personal_knowledge_manager.api.FoldersApi;
import be.oz.personal_knowledge_manager.model.FolderDTO;
import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;
import be.oz.personal_knowledge_manager.pkm.folder.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FolderController implements FoldersApi {
    private final GetFoldersUseCase getFoldersUseCase;
    private final DeleteFolderByIdUseCase deleteFolderByIdUseCase;
    private final GetFolderNameByIdUseCase getFolderNameByIdUseCase;
    private final GetFolderIdByNameUseCase getFolderIdByNameUseCase;
    private final CreateOrUpdateFolderUseCase createOrUpdateFolderUseCase;

    @Override
    public ResponseEntity<FolderDTO> createOrUpdateFolder(FolderDTO folderDTO) {
        var folder = createOrUpdateFolderUseCase.execute(Folder.builder()
                .name(folderDTO.getName())
                .id(folderDTO.getId())
                .icon(folderDTO.getIcon())
                .build());
        return ResponseEntity.ok(FolderDTO.builder()
                .id(folder.getId())
                .name(folder.getName())
                .icon(folder.getIcon())
                .build());
    }

    @Override
    public ResponseEntity<Void> deleteFolderById(String id) {
        deleteFolderByIdUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

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
    public ResponseEntity<List<FolderDTO>> getFolders(String name) {
        if (name != null && !name.isBlank()) {
            var folderId = getFolderIdByNameUseCase.execute(name);

            if (folderId != null) {
                return ResponseEntity.ok(Collections.singletonList(
                        FolderDTO.builder().id(folderId).build()
                ));
            }
            return ResponseEntity.ok(Collections.emptyList());
        }

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
