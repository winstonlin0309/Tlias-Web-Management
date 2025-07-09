package com.project.mapper;

import com.project.pojo.Emp;
import com.project.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
//-----------------------------------------------原始分页查询实现 ------------------------------
//
//    /**
//     * 查询总记录数
//     *
//     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long count();
//
//    /**
//     * 查询分页列表
//     */
//    @Select("select e.*, d.name as deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc limit #{start}, #{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize);


    // --------------------- 通过PageHelper插件完成的查询实现 ---------------------------

    /**
     * 条件查询分页列表
     */
    public List<Emp> list(EmpQueryParam queryParam);


    /**
     * 新增员工基本信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values (#{username}, #{password}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);


    void deleteByIds(List<Integer> ids);

    @Select("select * from emp where id = #{id}")
    Emp getById(Integer id);


    void updateById(Emp emp);

    @Select("SELECT id, username, name FROM emp WHERE username = #{username} AND password = #{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
