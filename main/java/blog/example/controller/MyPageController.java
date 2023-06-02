package blog.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.example.model.entity.BlogEntity;
import blog.example.model.entity.UserEntity;
import blog.example.service.BlogService;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/mypage/blog")
@Controller
public class MyPageController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;
	
	@GetMapping("/list")
	public String getMyPageListPage(Model model) {
		UserEntity userEntity = (UserEntity) session.getAttribute("user");
		Long userId = userEntity.getUserId();
		List<BlogEntity> blogs= blogService.getMyBlogData(userId);
		model.addAttribute("userData",blogs);
		   return "userPage.html";
	}
	

	
//Update
	
	@PostMapping("/update")
	public String blogUpdate(@RequestParam Long blogId,
	                         @RequestParam String blogTitle,
	                         @RequestParam String content,
	                         Model model) {
	    // 从会话中获取当前用户信息
	    UserEntity userEntity = (UserEntity) session.getAttribute("user");
	    Long userId = userEntity.getUserId();

	    // 调用blogService.editBlogPost()方法来更新博客内容
	    if (blogService.editBlogPost(blogId, blogTitle, content, userId)) {
	        // 更新成功，重定向到博客列表页面
	        return "redirect:/user/blog/homepage";
	    } else {
	        // 更新失败，向模型中添加错误消息
	        model.addAttribute("errorMessage", "更新に失敗しました");
	        return "userPage.html";
	    }
	}

	
//delete
	@PostMapping("/delete")
	public String blogDelete(@RequestParam Long blogId,Model model) {
	    // 调用blogService.deleteBlog()方法删除博客
	    blogService.deleteBlog(blogId);
	    
	    model.addAttribute("DeleteDetailMessage",  "記事削除に失敗しました");
	    // 删除成功，重定向到博客列表页面
	    return "redirect:/mypage/blog/list";
	}


	}



