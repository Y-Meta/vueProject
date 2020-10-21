package com.ysl.studySpringbootAndVue.controller;

import com.ysl.studySpringbootAndVue.vo.ResultUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/getUserImg")
    public ResultUtils getUserImg(){
        return ResultUtils.succeed("http://8.129.162.69:8080/test.jpg");
    }
}
