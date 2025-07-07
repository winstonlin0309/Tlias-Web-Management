package com.project.mapper;

import com.project.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门
     */
    @Select("select * from dept order by update_time desc")
    List<Dept> findAll();

    /**
     * 根据ID删除部门
     */
    @Delete("delete from dept where id = #{id)")
    void deleteById(Integer id);

    /**
     * 新增部门
     */
    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    void insert(Dept dept);

    /**
     * 根据ID查找部门
     */
    @Select("select * from dept where id = #{id}")
    public Dept getById(Integer id);

    /**
     * 更新部门数据
     */
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
