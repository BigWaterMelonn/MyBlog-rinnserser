package blog.example.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.example.model.entity.BlogEntity;
import blog.example.model.entity.UserEntity;
import blog.example.service.BlogService;
import blog.example.service.UserService;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/blog")
@Controller
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;
	@Autowired
	private UserService userService;

	
	
	@GetMapping("/homepage")
	public String getHomePage(Model model) {
	    List<BlogEntity> blogEntity = blogService.getAllBlogs();
	    model.addAttribute("contents", blogEntity);
	    
	    UserEntity userEntity = (UserEntity) session.getAttribute("user");
	    if (userEntity != null) {
	        String userName = userEntity.getUserName();
	        model.addAttribute("userName", userName);
	    }
	    
	    return "homepage.html";
	}

	 
	 
	@GetMapping("/register")
	public String getRegisterPage() {
		   return "articlesPage.html";
	}
	
	
	/*
//details界面
	@GetMapping("/details")
	public String getBlogDetailsPage(Model model, @RequestParam("blogId") Long blogId) {
	    // 根据博客ID从数据库中获取博客信息
	    BlogEntity blog = blogService.getBlogById(blogId);
	    
	    // 将博客信息添加到Model中
	    model.addAttribute("blog", blog);
	    
	    // 返回详情页模板
	    return "details.html";
	}*/

	@GetMapping("/details/{blogId}")
	public String getBlogDetails(@PathVariable Long blogId, Model model) {
	    BlogEntity blog = blogService.getBlogById(blogId);
	    model.addAttribute("blog", blog);
	    return "details.html";
	}
	
	
	
	
	@PostMapping("/register/process")
	public String getBlogRegister(@RequestParam String blogTitle,@RequestParam String content,@RequestParam("imageName") MultipartFile imageName) {
		UserEntity userEntity = (UserEntity) session.getAttribute("user");
		Long userId = userEntity.getUserId();
		/**
		 * まず、MultipartFileを使用して、リクエストから送信されたファイルを取得しています。
		 * blogImage.getOriginalFilename()を使用して、アップロードされた画像ファイルの名前を取得し、
		 * Fileを使用して、アップロードするファイルの保存先を指定しています。
		 * ファイルが保存される場所は、"./src/main/resources/static/blog-img/"です。**/
		/**
		 * blogImage.getBytes()を使用して、ファイルのバイナリデータを取得し、BufferedOutputStreamを使用して、
		 * バッファにファイルのバイナリデータを書き込みます。そして、out.close()を呼び出すことで、
		 * バッファを閉じて、ファイルの書き込みを終了させます。
		 * このように、ファイルをアップロードするために、Java標準ライブラリのjava.ioパッケージを使用して、
		 * ファイル操作を行います。**/
		//画像ファイル名を取得する
		String fileName = imageName.getOriginalFilename();
		//ファイルのアップロード処理
		try {
			//画像ファイルの保存先を指定する。
			File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
			//画像ファイルからバイナリデータを取得する
			byte[] bytes = imageName.getBytes();
			//画像を保存（書き出し）するためのバッファを用意する
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
			//画像ファイルの書き出しする。
			out.write(bytes);
			//バッファを閉じることにより、書き出しを正常終了させる。
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(blogTitle);
		System.out.println(content);
		System.out.println(fileName);
		
		blogService.createBlog(blogTitle,content, fileName, userId);
		return "redirect:/user/blog/homepage";
	}
	

}