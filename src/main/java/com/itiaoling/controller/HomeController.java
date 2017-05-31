package com.itiaoling.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuhaibao on 15/10/31.
 */
@RestController
@RequestMapping("/")
public class HomeController {


    @GetMapping(value={"","/"})
    public String index(){

        return "Hello World";

    }

}
