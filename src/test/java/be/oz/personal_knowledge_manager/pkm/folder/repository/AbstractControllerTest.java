package be.oz.personal_knowledge_manager.pkm.folder.repository;

import be.oz.personal_knowledge_manager.pkm.folder.usecase.GetFolderNameByIdUseCase;
import be.oz.personal_knowledge_manager.pkm.folder.usecase.GetFoldersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FolderController.class)
@ComponentScan(
        basePackages = "be.oz.personal_knowledge_manager.pkm.folder.repository",
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
    protected GetFoldersUseCase getFoldersUseCase;

    @MockitoBean
    protected GetFolderNameByIdUseCase getFolderNameByIdUseCase;
}
