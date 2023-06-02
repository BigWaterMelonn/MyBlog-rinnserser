package blog.example.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name = "blog_data")
public class BlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long blogId;

    @Column(name = "blog_title")
    private String blogTitle;

    @Column(name = "blog_content")
    private String blogContent;

    @Column(name = "blog_image")
    private String blogImage;

    @Column(name = "user_id")
    private Long userId;
//一个实体对象关联到另一个实体对象
    
//当设置为 LAZY 时，关联实体对象在访问时才会被加载。
//只有在访问 user 属性时，才会触发加载关联的 UserEntity 对象。
//这可以提高性能，避免在不需要关联对象时加载它们。
    //@ManyToOne(fetch = FetchType.LAZY)
    
    
    //@JoinColumn(name = "user_id")  //指定关联的数据库列
    //private UserEntity user;
    
    
//无参构造
	public BlogEntity() {
	}
	

//没有主键的有参构造
	public BlogEntity(String blogTitle, String blogContent, String blogImage, Long userId) {
		this.blogTitle = blogTitle;
		this.blogContent = blogContent;
		this.blogImage = blogImage;
		this.userId = userId;
	}



//getter和setter
public Long getBlogId() {
	return blogId;
}


public void setBlogId(Long blogId) {
	this.blogId = blogId;
}

public String getBlogTitle() {
	return blogTitle;
}

public void setBlogTitle(String blogTitle) {
	this.blogTitle = blogTitle;
}

public String getBlogContent() {
	return blogContent;
}

public void setBlogContent(String blogContent) {
	this.blogContent = blogContent;
}

public String getBlogImage() {
	return blogImage;
}

public void setBlogImage(String blogImage) {
	this.blogImage = blogImage;
}


public Long getUserId() {
	return userId;
}

public void setUserId(Long userId) {
	this.userId = userId;
}



  
}