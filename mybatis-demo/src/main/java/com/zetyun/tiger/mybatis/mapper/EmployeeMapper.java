package com.zetyun.tiger.mybatis.mapper;

import com.zetyun.tiger.mybatis.bean.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EmployeeMapper {

    //查询单个员工
    @Select("select * from employee where id = #{id}")
    Employee getEmpById(Integer id);

    //查询所有员工
    @Select("select * from employee")
    List<Employee> getAll();

    //新增员工
    @Insert("insert into employee(last_name,gender,email) values(#{lastName},#{gender},#{email})")
    void insertEmployee(Employee e);

    //更新员工
    @Update("update employee set last_name = #{lastName} , gender = #{gender} ,email = #{email} where id = #{id}")
    void updateEmployee(Employee e);

    //删除员工
    @Delete("delete from employee where id = #{id}")
    void deleteEmployeeById(Integer id);

    /*
      如果方法中有多个参数，此时有多个选择方案。
          ①把多个参数封装为bean的属性， xxx 还写 bean的属性名即可
                  繁琐
          ②可以把多个参数封装为一个Map集合， xxx 写map集合中key的名字
                  繁琐
                  getEmpByCondition(Map map)
                      map: { gender = 'male',id = 1   }

                xxx实现: where id > #{id} and gender = #{xxx}
          ③可以使用@Param("xx")注解标注在参数前，之后 可以使用 xx 来引用参数。

   */
    @Select("select * from employee where id > #{b} and gender = #{a}")
    List<Employee> getEmpByCondition(@Param("a") String gender, @Param("b") Integer id);
}
