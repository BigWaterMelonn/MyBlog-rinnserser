package blog.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.example.model.entity.UserEntity;
import blog.example.service.UserService;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
/**
 * @Controller注解用于将此类注册到DI容器中，通过Spring Framework的组件扫描功能实现。
 * 这样，UserRegisterController类就可以被注入到其他组件中，并在系统中进行统一管理。
 * 组件扫描是Spring Framework中的一个功能，它自动检测和注册使用注解配置的类到DI容器中。
 * DI（Dependency Injection）容器是Spring Framework提供的一种管理应用程序所需对象的机制。
 * 通常情况下，应用程序需要的对象是通过使用new运算符进行实例化的，
 * 但使用DI容器可以自动进行对象的创建和注入。
 **/
@Controller
public class UserLoginController {
	/**
	 * @Autowired注解用于让DI容器自动注入UserService实例。
	 * 这样，UserRegisterController类就可以调用UserService类的方法，执行登录逻辑。
	 **/
	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;
	/**
	 * @GetMapping注解用于配置对HTTP GET请求的映射。
	 * 由于指定了"/login"，因此该端点可通过"/user/login"访问。
	 * getUserLoginPage()方法返回"login.html"，用于显示登录页面。
	 **/
	@GetMapping("/login")
	public String getUserLoginPage() {
		return "login.html";
	}
	

	/**
	 * @PostMapping注解表示该方法用于处理HTTP POST请求。
	 * "/login/process"指定了该方法处理的请求的URL，位于"/user"下。
	 * public String login(@RequestParam String email, @RequestParam String password)表示该方法接收请求参数。
	 * @RequestParam注解表示该方法的参数对应请求参数的值。
	 * 在这里，email和password作为请求参数传递。
	 **/
	@PostMapping("/login/process")
	public String login(@RequestParam String username, @RequestParam String password) {
		/**
		 * userService.loginAccount(name, password)调用UserService的loginAccount方法，
		 * 用指定的邮箱和密码检查是否可以登录账户。
		 * 如果登录成功，将登录信息存储在会话中，并重定向到"redirect:/user/blog/list"；
		 * 否则，重定向到"redirect:/user/login"。
		 **/
		UserEntity userList = userService.loginAccount(username, password);
		if (userList == null) {
	//model.addAttribute("Error Message", "Invalid email or password. Please try again.");
			return "redirect:/user/login";
		} else {
			session.setAttribute("user", userList);
			return "redirect:/user/blog/homepage";
		}
		/**
		 * 该代码处理用户登录功能。
		 * 如果用户访问登录页面(/user/login)，返回"login.html"以显示登录表单。
		 * 当用户提交登录表单时，调用login()方法，进而调用UserService的loginAccount()方法进行账户认证。
		 * 如果认证成功，将重定向到博客页面(/user/blog/list)；
		 * 如果认证失败，将重定向到登录页面(/user/login)。
		 **/
	}
}