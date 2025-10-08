package org.example.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.usercenter.constant.UserConstant;
import org.example.usercenter.model.domain.User;
import org.example.usercenter.model.request.UserLoginRequest;
import org.example.usercenter.model.request.UserRegisterRequest;
import org.example.usercenter.service.UserService;
import org.example.usercenter.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LinZeyuan
 * @description 用户接口
 * @createDate 2025/9/19 16:07
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /*
        用户注册
     */
    @PostMapping("/register")
    private Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userCode = userRegisterRequest.getUserCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, userCode)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword, userCode);
    }

    /*
        用户登录
     */
    @PostMapping("/login")
    private User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.doLogin(userAccount, userPassword, request);
    }

    @PostMapping("/logout")
    private Integer userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return userService.userLogout(request);
    }

    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest req) {
        User currentUser = (User) req.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (currentUser == null) {
            return null;
        }
        long id = currentUser.getId();
        // todo 校验用户是否合法
        User user = userService.getById(id);
        return userService.getSafetyUser(user);
    }

    @GetMapping("/search")
    private List<User> searchUsers(@RequestParam(value = "name", required = false) String userName, HttpServletRequest req) {
        // 用户鉴权
        if (!isAdmin(req)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("name", userName);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    private boolean deleteUsers(@RequestParam("id") long id, HttpServletRequest req) {
        if (!isAdmin(req)) return false;
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    private boolean isAdmin(HttpServletRequest req) {
        // 用户鉴权
        User user = (User) req.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        return user != null && user.getRole() == UserConstant.ADMIN_ROLE;
    }

}
