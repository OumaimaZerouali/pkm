package be.oz.personal_knowledge_manager.folder.repository;

import be.oz.personal_knowledge_manager.folder.domain.Folder;

public interface FolderRepository {
    Folder getFolderById(String id);
}
