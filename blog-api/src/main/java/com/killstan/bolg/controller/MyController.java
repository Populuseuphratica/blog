package com.killstan.bolg.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/30 15:42
 */
@RestController
public class MyController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
