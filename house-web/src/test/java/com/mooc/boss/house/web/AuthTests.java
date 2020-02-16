package com.mooc.boss.house.web;

import com.mooc.boss.house.biz.service.UserService;
import com.mooc.boss.house.common.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthTests {

    @Autowired
    private UserService userService;

    @Test
    public void testAuth() {
        User user = userService.auth("405990230@qq.com", "123456789");
        assert user != null;
        System.out.println(user.getAboutme());
    }

}