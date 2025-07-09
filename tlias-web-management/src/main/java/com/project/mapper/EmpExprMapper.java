package com.project.mapper;

import com.project.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    /**
     * 批量保存员工经历数据
     */
    void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据员工id删除工作经历信息-
     */
    void deleteByEmpIds(List<Integer> empIds);

    @Select("select * from emp_expr where emp_id = #{empId}")
    List<EmpExpr> getByEmpId(Integer empId);
}
