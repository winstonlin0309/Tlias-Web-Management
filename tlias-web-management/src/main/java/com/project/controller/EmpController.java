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
import java.util.List;

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

    /**
     * 新增员工
     */
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("保持员工数据: {}", emp);
        empService.save(emp);
        return Result.success();
    }

    /**
     * 删除员工
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工数据:{}", ids);
        empService.delete(ids);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(empService.getById(id));
    }

    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工数据:{}", emp);
        empService.update(emp);
        return Result.success();
    }
}


