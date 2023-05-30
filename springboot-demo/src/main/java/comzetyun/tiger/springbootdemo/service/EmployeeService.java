package comzetyun.tiger.springbootdemo.service;

import comzetyun.tiger.springbootdemo.bean.Employee;

import java.util.List;

public interface EmployeeService
{

    //查询单个员工
    Employee getEmpById(Integer id);
    //删除单个员工
    void deleteEmpById(Integer id);
    //更新单个员工
    void updateEmp(Employee e);
    //新增单个员工
    void insertEmp(Employee e);
    //查询所有员工
    List<Employee> getAllEmp();


}
