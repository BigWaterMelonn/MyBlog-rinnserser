package blog.example.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import blog.example.model.entity.BlogEntity;
import blog.example.model.entity.UserEntity;
import blog.example.service.BlogService;
import blog.example.service.UserService;

class BlogControllerTest {

    @Mock
    private BlogService blogService;

    @Mock
    private MockHttpSession session;

    @Mock
    private UserService userService;

    @InjectMocks
    private BlogController blogController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
    }

    @Test
    void testGetHomePage() {
        // 模拟数据
        List<BlogEntity> blogEntityList = new ArrayList<>();
        blogEntityList.add(new BlogEntity());
        when(blogService.getAllBlogs()).thenReturn(blogEntityList);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("测试用户");
        when(session.getAttribute("user")).thenReturn(userEntity);

        // 测试
        Model model = mock(Model.class);
        String viewName = blogController.getHomePage(model);

        // 验证
        verify(blogService, times(1)).getAllBlogs();
        verify(model, times(1)).addAttribute(eq("contents"), anyList());
        verify(model, times(1)).addAttribute(eq("userName"), eq("测试用户"));
        assert viewName.equals("homepage.html");
    }

    @Test
    void testGetRegisterPage() {
        String viewName = blogController.getRegisterPage();
        assert viewName.equals("articlesPage.html");
    }

    @Test
    void testGetBlogDetails() {
        Long blogId = 1L;
        BlogEntity blogEntity = new BlogEntity();
        when(blogService.getBlogById(blogId)).thenReturn(blogEntity);

        Model model = mock(Model.class);
        String viewName = blogController.getBlogDetails(blogId, model);

        verify(blogService, times(1)).getBlogById(blogId);
        verify(model, times(1)).addAttribute(eq("blog"), eq(blogEntity));
        assert viewName.equals("details.html");
    }

    }

