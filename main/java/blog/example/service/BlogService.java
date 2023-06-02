package blog.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.example.model.dao.BlogDao;
import blog.example.model.entity.BlogEntity;

import java.util.List;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;

	public void createBlog(String title, String content, String image, Long userId) {
		BlogEntity blog = new BlogEntity();
		blog.setBlogTitle(title);
		blog.setBlogContent(content);
		blog.setBlogImage(image);
		blog.setUserId(userId);
		blogDao.save(blog);
	}

	public List<BlogEntity> getAllBlogs() {
		return blogDao.findAll();
	}

	public BlogEntity getBlogById(Long blogId) {
		return blogDao.findByBlogId(blogId);
	}

	public List<BlogEntity> getMyBlogData(Long userId) {
		return blogDao.findByUserId(userId);
	}

	public void deleteBlog(Long blogId) {
		blogDao.deleteByBlogId(blogId);
	}



	//editBlog相关方法    
	//通过传入的blogId查找对应的博客实体，并检查该博客实体的userId是否与传入的userId相匹配
	//如果匹配，则更新博客标题和内容，并保存到数据库中。

	@Transactional
	public boolean editBlogPost(Long blogId, String blogTitle, String blogContent, Long userId) {
		BlogEntity blogEntity = blogDao.findByBlogId(blogId);

		if (blogEntity == null) {
			return false;
		}else {
			 blogEntity.setBlogId(blogId);
			 blogEntity.setBlogTitle(blogTitle);
			 blogEntity.setBlogContent(blogContent);
			 blogEntity.setUserId(userId);
			 blogDao.save(blogEntity);
        return true;
		}



	}
}