package blog.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.example.model.dao.UserDao;
import blog.example.model.entity.UserEntity;



@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    // 登录账户
    public UserEntity loginAccount(String username, String password) {
        // 检查用户名和密码是否为空
        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }
        
        // 通过用户名和密码在数据库中查找用户实体
        UserEntity userEntity = userDao.findByUserNameAndUserPassword(username, password);
        
        // 如果找不到用户实体，返回null；否则返回用户实体
        return userEntity;
    }

 // 创建账户
    public boolean createAccount(String userName, String userMail, String password, String userTel) {
        // 检查用户名、邮箱、密码和电话号码是否为空
        if (userName.isEmpty() || userMail.isEmpty() || password.isEmpty() || userTel.isEmpty()) {
            return false;
        }
        
        // 通过用户邮箱在数据库中查找用户实体
        UserEntity userEntity = userDao.findByUserMail(userMail);
        
        // 如果找不到用户实体，继续检查用户名是否已存在
        if (userEntity == null) {
            // 检查用户名是否已存在
            UserEntity existingUser = userDao.findByUserName(userName);
            
            // 如果用户名已存在，返回false，表示创建失败
            if (existingUser != null) {
                return false;
            }
            
            // 用户名可用，创建新的用户实体并保存到数据库
            userDao.save(new UserEntity(userName, password, userMail, userTel));
            return true;
        } else {
            // 找到用户实体，返回false，表示创建失败
            return false;
        }
    }


	}