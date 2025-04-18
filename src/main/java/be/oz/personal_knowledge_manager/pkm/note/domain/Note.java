package be.oz.personal_knowledge_manager.pkm.note.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class Note {
    private String id;
    private String title;
    private String content;
    private String content_html;
    private String author;
    private String source_url;
    private String folder;
    private boolean todo;
    private LocalDateTime todo_due;
    private boolean todo_completed;
    private LocalDateTime created;
    private LocalDateTime updated;
}
