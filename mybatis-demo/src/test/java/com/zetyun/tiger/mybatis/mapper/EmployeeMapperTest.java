package com.zetyun.tiger.mybatis.mapper;

import com.zetyun.tiger.mybatis.bean.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeMapperTest {

    private SqlSessionFactory sqlSessionFactory;


    {
        //读取配置文件
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //从配置文件中构造SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

    }

    @Test
    public void getEmpById() {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {

            //编写业务代码  mapper就是EmployeeMapper的一个实例
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            Employee employee = mapper.getEmpById(1);

            System.out.println(employee);

        } finally {
            sqlSession.close();
        }

    }

    @Test
    public void getAll() {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {

            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            List<Employee> employees = mapper.getAll();

            System.out.println(employees);

        } finally {
            sqlSession.close();
        }

    }


    @Test
    public void insertEmployee() {

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        try {

            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            Employee employee = new Employee(null, "zhangsan", "male", "zhangsan@qq.com");

            mapper.insertEmployee(employee);

        } finally {
            sqlSession.close();
        }


    }

    @Test
    public void updateEmployee() {

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        try {

            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            Employee e = mapper.getEmpById(2);

            e.setLastName("LiSi");

            mapper.updateEmployee(e);

        } finally {
            sqlSession.close();
        }
    }

    /*
            读： 查询
            写： insert,update,delete
                必须提交事务才有效。
                原生JDBC，自动提交事务。
                Mybatis中需要进行设置！
     */
    @Test
    public void deleteEmployeeById() {

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        try {

            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            mapper.deleteEmployeeById(1);


        } finally {
            sqlSession.close();
        }

    }

    @Test
    public void selectByCondition() {

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        try {

            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            List<Employee> emps = mapper.getEmpByCondition("male", 1);

            System.out.println(emps);


        } finally {
            sqlSession.close();
        }

    }
}