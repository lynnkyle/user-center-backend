package org.example.usercenter;

import org.example.usercenter.mapper.UserMapper;
import org.example.usercenter.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        List<User> users = userMapper.selectList(null);
        Assert.notNull(users);
        users.forEach(System.out::println);
    }

}