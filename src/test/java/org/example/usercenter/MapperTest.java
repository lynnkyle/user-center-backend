package org.example.usercenter;

import org.example.usercenter.model.domain.User;
import org.example.usercenter.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MapperTest {

    @Resource
    private UserService userService;

    @Test
    public void testSelect() {
        User user = new User();
        user.setUserName("lzy");
        user.setAccount("");
        user.setPassword("");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setPhone("");
        user.setEmail("");
        boolean save = userService.save(user);
        System.out.println(save);
        Assertions.assertEquals(save, true);
    }

}