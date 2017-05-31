package com.itiaoling.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itiaoling.model.User;
import com.itiaoling.service.UserService;

/**
 * Created by liuhaibao on 15/10/31.
 */
@Controller
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    UserService usersService;

    @RequestMapping(value={"","/"}, method=RequestMethod.GET)
    public String index(){

        User user = new User();
        user.setName("abc");

        @SuppressWarnings("unused")
		User users = usersService.findByName("abc");
        return "posts/index";

    }

}
