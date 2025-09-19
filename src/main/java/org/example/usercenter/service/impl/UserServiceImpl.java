package org.example.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.usercenter.mapper.UserMapper;
import org.example.usercenter.model.domain.User;
import org.example.usercenter.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LinZeyuan
 * @description 用户服务实现类
 * @createDate 2025-09-18 17:35:27
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 加密盐值
    private static final String SALT = "MD5";

    private static final String USER_LOGIN_STATE = "user_login_state";

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        // 1.校验用户账号
        if (userAccount.length() < 4) {
            return -1;
        }
        String validPattern = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.matches()) {
            return -1;
        }
        // 2.校验用户密码
        if (userPassword.length() < 8) {
            return -1;
        }
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 校验用户账号-重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        long count = userMapper.selectCount(queryWrapper.eq("account", userAccount));
        if (count > 0) {
            return -1;
        }
        // 3.密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 4.插入数据
        User user = new User();
        user.setAccount(userAccount);
        user.setPassword(encryptPassword);
        boolean result = save(user);
        if (!result) {
            return -1;
        }
        // 5.返回用户ID
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        // 1. 校验用户账号
        if (userAccount.length() < 4) {
            return null;
        }
        String validPattern = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.matches()) {
            return null;
        }
        // 2. 校验用户密码
        if (userPassword.length() < 8) {
            return null;
        }
        // 3. 校验密码是否正确
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("account", userAccount);
        queryWrapper.eq("password", encryptPassword);
        User userFromDb = userMapper.selectOne(queryWrapper);
        if (userFromDb == null) {
            log.info("user login failed");
            return null;
        }
        // 5. 返回用户信息(脱敏)
        User safetydUser = new User();
        safetydUser.setId(userFromDb.getId());
        safetydUser.setUserName(userFromDb.getUserName());
        safetydUser.setAccount(userFromDb.getAccount());
        safetydUser.setAvatarUrl(userFromDb.getAvatarUrl());
        safetydUser.setGender(userFromDb.getGender());
        safetydUser.setEmail(userFromDb.getEmail());
        safetydUser.setPhone(userFromDb.getPhone());
        safetydUser.setStatus(userFromDb.getStatus());
        safetydUser.setCreateTime(userFromDb.getCreateTime());
        // 4. 保存用户登录态
        HttpSession session = request.getSession();
        session.setAttribute(USER_LOGIN_STATE, userFromDb);
        return safetydUser;

    }
}




