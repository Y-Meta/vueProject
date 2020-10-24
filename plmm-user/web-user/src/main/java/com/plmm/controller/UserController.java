package com.plmm.controller;

import com.plmm.common.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.plmm.common.base.Result;

@RestController
@RequestMapping("test")
public class UserController {
    @GetMapping("/getUserImg")
    public Result getUserImg(){
        return ResultUtil.sucess("http://8.129.162.69:8080/test.jpg");
    }
}
