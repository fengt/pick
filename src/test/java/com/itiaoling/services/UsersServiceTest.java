package com.itiaoling.services;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itiaoling.Application;
import com.itiaoling.model.User;
import com.itiaoling.service.UserService;

/**
 * Created by liuhaibao on 15/11/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ImportResource("classpath:config/applicationContext.xml")
@Transactional
public class UsersServiceTest {

	@Autowired
    private UserService userService;


//    @Test
    public void testSave(){
        User user = new User();

        String name = "assss";
        user.setName(name);
        userService.saveUser(user);
        assertThat("", user.getId(), greaterThan(0L));
        assertEquals(name, user.getName());
    }


//    @Test
    public void testFindByName(){
        User user = new User();

        String name = "assss";
        user.setName(name);
        int insertCount = userService.saveUser(user);
        assertEquals(1, insertCount);
        
        User user2 = userService.findByName(name);
        assertEquals(name, user2.getName());
    }
}
