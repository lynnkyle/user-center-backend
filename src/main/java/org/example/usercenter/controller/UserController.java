package org.example.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.usercenter.common.BaseResponse;
import org.example.usercenter.common.ErrorCode;
import org.example.usercenter.common.ResultUtils;
import org.example.usercenter.constant.UserConstant;
import org.example.usercenter.exception.BusinessException;
import org.example.usercenter.model.domain.User;
import org.example.usercenter.model.request.UserLoginRequest;
import org.example.usercenter.model.request.UserRegisterRequest;
import org.example.usercenter.service.UserService;
import org.example.usercenter.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
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
@CrossOrigin(origins = {"http://user-front.linzeyuan.site"}, allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {
    @Resource
    private UserService userService;

    /*
        用户注册
     */
    @PostMapping("/register")
    private BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userCode = userRegisterRequest.getUserCode();
        long userId = userService.userRegister(userAccount, userPassword, checkPassword, userCode);
        return ResultUtils.success(userId, "用户注册成功, 并返回用户ID");
    }

    /*
        用户登录
     */
    @PostMapping("/login")
    private BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        User user = userService.doLogin(userAccount, userPassword, request);
        return ResultUtils.success(user, "用户登录成功, 并返回用户信息");
    }

    @PostMapping("/logout")
    private BaseResponse<Void> userLogout(HttpServletRequest request) {
        userService.userLogout(request);
        return ResultUtils.success(null, "用户退出登录成功");
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest req) {
        User currentUser = (User) req.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long id = currentUser.getId();
        // todo 校验用户是否合法
        User user = userService.getById(id);
        user = userService.getSafetyUser(user);
        return ResultUtils.success(user, "当前用户信息获取成功,并返回当前用户信息");
    }

    @GetMapping("/search")
    private BaseResponse<List<User>> searchUsers(@RequestParam(value = "name", required = false) String userName, HttpServletRequest req) {
        // 用户鉴权
        if (!isAdmin(req)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("name", userName);
        }
        List<User> userList = userService.list(queryWrapper);
        userList = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(userList, "用户列表获取成功, 并返回用户列表");
    }

    @DeleteMapping("/delete")
    private BaseResponse<Boolean> deleteUsers(@RequestParam("id") long id, HttpServletRequest req) {
        if (!isAdmin(req)) throw new BusinessException(ErrorCode.NO_AUTH);
        if (id <= 0) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        boolean res = userService.removeById(id);
        return ResultUtils.success(res, "用户删除成功");
    }

    private boolean isAdmin(HttpServletRequest req) {
        // 用户鉴权
        User user = (User) req.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        return user != null && user.getRole() == UserConstant.ADMIN_ROLE;
    }

}
