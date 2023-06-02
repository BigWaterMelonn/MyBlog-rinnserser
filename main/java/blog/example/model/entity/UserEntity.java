package blog.example.model.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_data")
public class UserEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_mail")
    private String userMail;

    @Column(name = "user_tel")
    private String userTel;
    
//默认无参构造
	public UserEntity() {
	}
//没有主键的构造

public UserEntity(String userName, String userPassword, String userMail, String userTel) {
	this.userName = userName;
	this.userPassword = userPassword;
	this.userMail = userMail;
	this.userTel = userTel;
}


//getter和setter
public Long getUserId() {
	return userId;
}

public void setUserId(Long userId) {
	this.userId = userId;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getUserPassword() {
	return userPassword;
}

public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}

public String getUserMail() {
	return userMail;
}

public void setUserMail(String userMail) {
	this.userMail = userMail;
}

public String getUserTel() {
	return userTel;
}

public void setUserTel(String userTel) {
	this.userTel = userTel;
}
	
}
  