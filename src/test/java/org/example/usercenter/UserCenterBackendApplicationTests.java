package org.example.usercenter;

import org.example.usercenter.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserCenterBackendApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        String userAccount = "";
        String userPassword = "123456";
        String checkPassword = "123456";
//        long l = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(l, -1);
        userAccount = "lzy";
        userPassword = "123456";
        checkPassword = "123456";
//        l = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(l, -1);
        userAccount = "lzy$";
        userPassword = "123456";
        checkPassword = "123456";
//        l = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(l, -1);
        userAccount = "lzyy";
        userPassword = "123456";
        checkPassword = "123456";
//        l = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(l, -1);
        userAccount = "lzykyle";
        userPassword = "12345678";
        checkPassword = "12345678";
//        l = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(l, -1);
        userAccount = "lzyv";
        userPassword = "12345678";
        checkPassword = "123456789";
//        l = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(l, -1);
        userAccount = "lzyv";
        userPassword = "12345678";
        checkPassword = "12345678";
//        l = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertNotEquals(l, -1);
    }

}
