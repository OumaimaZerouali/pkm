package be.oz.personal_knowledge_manager.pkm.note.repository;

import be.oz.personal_knowledge_manager.pkm.note.domain.Note;

import java.util.List;

public interface NoteRepository {
    List<Note> findAllNotes();
    Note createNote(Note note);
    Note getNoteById(String id);
    Note updateNoteById(Note note, String id);
    void deleteNoteById(String id);
}
