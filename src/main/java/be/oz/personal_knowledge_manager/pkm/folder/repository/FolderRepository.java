package be.oz.personal_knowledge_manager.pkm.folder.repository;

import be.oz.personal_knowledge_manager.pkm.folder.domain.Folder;

import java.util.List;
import java.util.Optional;

public interface FolderRepository {
    List<Folder> getFolders();
    Optional<Folder> getFolderById(String id);
    void deleteFolderById(String id);
    Folder updateFolderById(Folder folder, String id);
    Folder createFolder(Folder folder);
    String getFolderIdByName(String name);
}
