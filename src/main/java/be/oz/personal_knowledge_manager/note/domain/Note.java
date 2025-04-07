package be.oz.personal_knowledge_manager.note.domain;

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
    private LocalDateTime created;
    private LocalDateTime updated;
}
