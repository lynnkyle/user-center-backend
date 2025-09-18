package org.example.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.usercenter.mapper.UserMapper;
import org.example.usercenter.model.User;
import org.example.usercenter.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author LinZeyuan
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-09-18 17:35:27
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}




