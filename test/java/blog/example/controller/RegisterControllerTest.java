package blog.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import blog.example.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    private void prepareData() {
        // 使用Mockito的when方法设置了模拟行为：
        // 当调用userService.createAccount方法时，传入任意参数，返回true。
        when(userService.createAccount(any(), any(), any(), any())).thenReturn(true);
        when(userService.createAccount(eq(""),eq(""),eq(""),eq(""))).thenReturn(false);
    }

    // 测试获取注册页面是否成功
    @Test
    public void testGetRegisterPage_Succeed() throws Exception {
        RequestBuilder request = get("/user/register"); // 创建一个GET请求的RequestBuilder，访问路径为"/user/register"
        mockMvc.perform(request)
                .andExpect(status().isOk()) // 期望响应状态为OK
                .andExpect(view().name("register")); // 期望返回的视图名称为"register"
    }

    // 测试注册成功的情况
    @Test
    public void testRegister_Success() throws Exception {
        RequestBuilder request = post("/user/register/process")
                .param("username", "1029water")
                .param("password", "1029")
                .param("email", "test1@test1.com")
                .param("tel", "123456789");

        mockMvc.perform(request)
                .andExpect(redirectedUrl("/user/login")); // 期望重定向到"/user/login"
    }

    // 测试注册失败的情况
    @Test
    public void testRegister_Failure() throws Exception {
        RequestBuilder request = post("/user/register/process")
                .param("username", "")
                .param("password", "")
                .param("email", "")
                .param("tel", "");

        mockMvc.perform(request)
                .andExpect(view().name("register.html")); // 期望响应的视图名称为"register.html"
    }
}
