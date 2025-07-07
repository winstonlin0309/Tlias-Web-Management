package com.project.controller;

import com.project.pojo.Emp;
import com.project.pojo.EmpQueryParam;
import com.project.pojo.PageResult;
import com.project.pojo.Result;
import com.project.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    EmpService empService;

    /**
     * 分页查询
     */
    @GetMapping
    public Result page(EmpQueryParam queryParam) {

        log.info("分页查询: {}", queryParam);
        PageResult<Emp> pageResult = empService.page(queryParam);
        return Result.success(pageResult);
    }
}


