package be.oz.personal_knowledge_manager.folder.repository;

import be.oz.personal_knowledge_manager.folder.domain.Folder;
import be.oz.personal_knowledge_manager.folder.usecase.GetFolderNameByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {
    private final GetFolderNameByIdUseCase getFolderNameByIdUseCase;

    @GetMapping("/{id}")
    private ResponseEntity<Folder> getFolderNameOnId(@PathVariable String id) {
        var folder = getFolderNameByIdUseCase.execute(id);

        if (folder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(folder);
    }
}
