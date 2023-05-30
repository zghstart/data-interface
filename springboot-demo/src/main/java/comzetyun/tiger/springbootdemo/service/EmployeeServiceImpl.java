package comzetyun.tiger.springbootdemo.service;


import comzetyun.tiger.springbootdemo.bean.Employee;
import comzetyun.tiger.springbootdemo.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Smexy on 2023
 *
 *  @Service的作用：
 *          ①标识当前类是一个业务模型
 *          ②容器扫描到后，会自动为这个类在容器中创建一个单例对象。
 */
@Service
public class EmployeeServiceImpl implements EmployeeService
{
    //需要读写数据库
    @Autowired
    private EmployeeMapper mapper;

    //具体的业务实现要参考公司的接口功能设计文档
    @Override
    public Employee getEmpById(Integer id) {

        System.out.println("查询之前 做事....");

        Employee e = mapper.getEmpById(id);

        System.out.println("查询之后 做事....");

        return e;
    }

    @Override
    public void deleteEmpById(Integer id) {

        mapper.deleteEmployeeById(id);

    }

    @Override
    public void updateEmp(Employee e) {
        mapper.updateEmployee(e);
    }

    @Override
    public void insertEmp(Employee e) {
        mapper.insertEmployee(e);
    }

    @Override
    public List<Employee> getAllEmp() {
        return mapper.getAll();
    }
}
