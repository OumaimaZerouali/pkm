package be.oz.personal_knowledge_manager.folder.repository;

import be.oz.personal_knowledge_manager.folder.domain.Folder;

import java.util.List;

public interface FolderRepository {
    Folder getFolderById(String id);
    List<Folder> getFolders();
}
