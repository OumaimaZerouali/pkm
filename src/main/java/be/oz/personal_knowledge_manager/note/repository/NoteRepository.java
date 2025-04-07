package be.oz.personal_knowledge_manager.note.repository;

import be.oz.personal_knowledge_manager.note.domain.Note;

import java.util.List;

public interface NoteRepository {
    List<Note> findAllNotes();
    Note createNote(Note note);
    Note getNoteById(String id);
}
