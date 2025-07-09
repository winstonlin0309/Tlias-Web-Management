package com.project.service;

import com.project.pojo.Emp;
import com.project.pojo.EmpQueryParam;
import com.project.pojo.LoginInfo;
import com.project.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {

    PageResult<Emp> page(EmpQueryParam queryParam);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getById(Integer id);

    void update(Emp emp);

    LoginInfo login(Emp emp);
}
