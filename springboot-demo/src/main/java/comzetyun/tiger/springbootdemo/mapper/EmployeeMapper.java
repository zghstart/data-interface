package comzetyun.tiger.springbootdemo.mapper;

import comzetyun.tiger.springbootdemo.bean.Employee;
import org.apache.ibatis.annotations.*;

import com.baomidou.dynamic.datasource.annotation.DS;


import java.util.List;

@Mapper
public interface EmployeeMapper
{
    //查询单个员工
    //为当方法指定数据源，根据就近原则选择
    @DS("mybatis")
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

}