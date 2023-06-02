package blog.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.example.model.entity.BlogEntity;
import jakarta.transaction.Transactional;


	@Repository
	public interface BlogDao extends JpaRepository<BlogEntity,Long> {
		List<BlogEntity> findByBlogTitle(String blogTitle);
		BlogEntity save(BlogEntity blogEntity);
		BlogEntity findByBlogId(Long blogId);
        List<BlogEntity> findByUserId(Long userId);
        @Transactional
        List<BlogEntity> deleteByBlogId(Long blogId);
	}
