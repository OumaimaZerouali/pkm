package be.oz.personal_knowledge_manager.pkm.note.repository;

import be.oz.personal_knowledge_manager.pkm.note.usecase.CreateOrUpdateNoteUseCase;
import be.oz.personal_knowledge_manager.pkm.note.usecase.DeleteNoteByIdUseCase;
import be.oz.personal_knowledge_manager.pkm.note.usecase.GetNoteByIdUseCase;
import be.oz.personal_knowledge_manager.pkm.note.usecase.GetNotesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoteController.class)
@ComponentScan(
        basePackages = "be.oz.personal_knowledge_manager.pkm.note.repository",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = ".*Controller"
        )
)
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected GetNotesUseCase getNotesUseCase;

    @MockitoBean
    protected GetNoteByIdUseCase getNoteByIdUseCase;

    @MockitoBean
    protected DeleteNoteByIdUseCase deleteNoteByIdUseCase;

    @MockitoBean
    protected CreateOrUpdateNoteUseCase createOrUpdateNoteUseCase;
}
