package com.project.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.mapper.EmpExprMapper;
import com.project.mapper.EmpMapper;
import com.project.pojo.*;
import com.project.service.EmpLogService;
import com.project.service.EmpService;
import com.project.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    EmpMapper empMapper;
    @Autowired
    EmpExprMapper empExprMapper;
    @Autowired
    EmpLogService empLogService;

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

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp) {
        try {
            //1. 保存员工基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            //2. 保存员工经历信息
            List<EmpExpr> exprList = emp.getExprList();

            if(!exprList.isEmpty()) {
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //3. 记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工" + emp);
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        //1. 删除员工基本信息
        empMapper.deleteByIds(ids);

        //2. 删除员工工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getById(Integer id) {
        Emp emp = empMapper.getById(id);
        emp.setExprList(empExprMapper.getByEmpId(emp.getId()));
        return emp;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        empExprMapper.deleteByEmpIds(Collections.singletonList(emp.getId()));

        empExprMapper.insertBatch(emp.getExprList());

    }

    @Override
    public LoginInfo login(Emp emp) {
        //1. 调用mapper接口, 根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);

        //2. 判断: 判断是否存在这个员工, 如果存在, 封装登入信息
        if(e != null) {
            log.info("登入成功, 员工信息: {}",e);

            //生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String token = JwtUtils.generateToken(claims);

            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), token);
        }

        //3. 不存在的话 返回null
        return null;
    }
}
