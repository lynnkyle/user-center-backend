package org.example.usercenter.service;

import org.example.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LinZeyuan
 * @description 用户服务
 * @createDate 2025-09-18 17:35:27
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @return 用户信息(脱敏)
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

}
