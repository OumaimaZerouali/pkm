package be.oz.personal_knowledge_manager.pkm.folder.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Folder {
    private String id;
    private String name;
    private String icon;
}
