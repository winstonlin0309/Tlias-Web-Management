package com.project.controller;

import com.project.pojo.Emp;
import com.project.pojo.LoginInfo;
import com.project.pojo.Result;
import com.project.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登入controller
 */
@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private EmpService empService;

    /**
     * 登入
     */
    @PostMapping
    public Result login(@RequestBody Emp emp) {
        log.info("登入: {}", emp);
        LoginInfo info = empService.login(emp);

        if(info != null) {
            return Result.success(info);
        }

        return Result.error("用户名或密码错误");
    }
}
