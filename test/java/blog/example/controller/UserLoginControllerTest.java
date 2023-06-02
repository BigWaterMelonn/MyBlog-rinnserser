package blog.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.example.model.entity.UserEntity;
import blog.example.service.UserService;
import jakarta.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService accountService;

    @BeforeEach
    private void prepareData() {
        // 准备测试数据
        UserEntity userEntity = new UserEntity("melon", "123", "test@test.com", "000999123");
        
        // 配置模拟的 UserService 对象的行为
        when(accountService.loginAccount(any(), any())).thenReturn(null); // 当任何用户名和密码参数时，模拟返回空值
        when(accountService.loginAccount(eq("melon"), eq("123"))).thenReturn(userEntity); // 当用户名为"melon"，密码为"123"时，模拟返回用户实体对象
    }

    @Test
    public void testGetLoginPage_Succeed() throws Exception {
        // 创建 GET 请求构造器，访问登录页面
        RequestBuilder request = MockMvcRequestBuilders.get("/user/login");
        
        // 执行请求并验证返回结果
        mockMvc.perform(request)
                .andExpect(view().name("login.html")); // 验证返回的视图名称为"login.html"
    }

    @Test
    public void testLogin_Succeed() throws Exception {
        // 创建 POST 请求构造器，模拟登录请求
        RequestBuilder request = MockMvcRequestBuilders.post("/user/login/process")
                .param("username", "melon") // 设置用户名参数为"melon"
                .param("password", "123"); // 设置密码参数为"123"

        // 执行请求并验证重定向到用户的主页
        MvcResult result = mockMvc.perform(request).andExpect(redirectedUrl("/user/blog/homepage")).andReturn();

        // 从请求中获取 HttpSession 对象
        HttpSession session = result.getRequest().getSession();

        // 从 HttpSession 中获取存储的用户对象
        UserEntity userList = (UserEntity) session.getAttribute("user");
        
        // 验证用户对象是否不为空
        assertNotNull(userList);
        
        // 验证用户对象的属性是否符合预期
        assertEquals("melon", userList.getUserName()); // 验证用户名为"melon"
        assertEquals("123", userList.getUserPassword()); // 验证密码为"123"
    } 
    @Test
    public void testGetLoginPage_Failed() throws Exception {
        // 创建 GET 请求构造器，并传入错误的用户名和密码参数
        RequestBuilder request = MockMvcRequestBuilders.get("/user/login")
                .param("username", "Bob") // 设置错误的用户名参数为"Bob"
                .param("password", "Bob123"); // 设置错误的密码参数为"Bob123"

        // 执行请求并验证返回的视图名称
        mockMvc.perform(request)
                .andExpect(view().name("login.html")); // 验证返回的视图名称为"login.html"
    }
}
