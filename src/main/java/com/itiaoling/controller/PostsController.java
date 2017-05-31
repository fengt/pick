package com.itiaoling.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itiaoling.model.User;
import com.itiaoling.service.UsersService;

/**
 * Created by liuhaibao on 15/10/31.
 */
@Controller
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    UsersService usersService;

    @RequestMapping(value={"","/"}, method=RequestMethod.GET)
    public String index(){

        User user = new User();
        user.setName("abc");

        @SuppressWarnings("unused")
		List<User> users = usersService.findByName("abc");
        return "posts/index";

    }

}
