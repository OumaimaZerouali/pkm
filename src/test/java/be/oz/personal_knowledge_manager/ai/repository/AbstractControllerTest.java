package be.oz.personal_knowledge_manager.ai.repository;

import be.oz.personal_knowledge_manager.pkm.folder.usecase.GetFolderNameByIdUseCase;
import be.oz.personal_knowledge_manager.pkm.note.usecase.CreateOrUpdateNoteUseCase;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AIController.class)
@ComponentScan(
        basePackages = "be.oz.personal_knowledge_manager.ai.repository",
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
    protected ChatModel chatModel;

    @MockitoBean
    protected GetFolderNameByIdUseCase getFolderNameByIdUseCase;

    @MockitoBean
    protected CreateOrUpdateNoteUseCase createOrUpdateNoteUseCase;
}
