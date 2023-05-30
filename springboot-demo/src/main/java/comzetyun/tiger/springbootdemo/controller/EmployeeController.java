package comzetyun.tiger.springbootdemo.controller;


import comzetyun.tiger.springbootdemo.bean.Employee;
import comzetyun.tiger.springbootdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Smexy on 2023
 */
@RestController
public class EmployeeController
{
    // employeeService = new EmployeeServiceImpl();
    // 从容器中找指定类型的对象，找到就赋值。找不到就报错。
    @Autowired
    private EmployeeService employeeService;

    // /emp? op=select&id=&lastname=&gender=&email=
    @RequestMapping(value = "/emp")
    public Object handleEmp(String op, Integer id, String lastname, String gender, String email) {

        //把参数封装为数据模型
        Employee employee = new Employee(id, lastname, gender, email);

        if ("insert".equals(op)){
            employeeService.insertEmp(employee);
            return "ok";
        } else {

            if (id == null) {
                return "id非法!";
            }

            switch (op) {
                case "select":
                    Employee emp = employeeService.getEmpById(id);
                    return emp == null ? "查无此人" : emp;


                case "delete":
                    employeeService.deleteEmpById(id);
                    return "ok";


                case "insert":


                case "update":
                    employeeService.updateEmp(employee);
                    return "ok";

                default:
                    return "ok";
            }
        }

    }

    @RequestMapping(value = "/getAllEmp")
    public Object handleGetAllEmp(){

        List<Employee> emps = employeeService.getAllEmp();

        return emps;

    }
}
