package com.project.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.mapper.EmpMapper;
import com.project.pojo.Emp;
import com.project.pojo.EmpQueryParam;
import com.project.pojo.PageResult;
import com.project.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    EmpMapper empMapper;

    /**
     *  原始分页查询
     *
    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        Long count = empMapper.count();
        Integer start = (page - 1) * pageSize;
        List<Emp> list = empMapper.list(start, pageSize);

        PageResult<Emp> result = new PageResult<>();
        result.setRows(list)
                .setTotal(count);

        return result;
    }
     */

    /**
     * 基于PageHelper分页查询
     */
    @Override
    public PageResult<Emp> page(EmpQueryParam queryParam) {
        //1. 设置分页参数
        PageHelper.startPage(queryParam.getPage(), queryParam.getPageSize());

        //2. 执行查询
        List<Emp> empList = empMapper.list(queryParam);

        //3. 解析结果, 并封装
        Page<Emp> p = (Page<Emp>)  empList;

        return new PageResult<Emp>().setTotal(p.getTotal())
                .setRows(p.getResult());
    }
}
