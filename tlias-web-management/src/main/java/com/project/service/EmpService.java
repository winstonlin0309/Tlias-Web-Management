package com.project.service;

import com.project.pojo.Emp;
import com.project.pojo.EmpQueryParam;
import com.project.pojo.PageResult;

import java.time.LocalDate;

public interface EmpService {

    PageResult<Emp> page(EmpQueryParam queryParam);
}
