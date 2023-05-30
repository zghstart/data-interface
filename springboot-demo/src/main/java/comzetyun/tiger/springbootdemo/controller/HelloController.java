package comzetyun.tiger.springbootdemo.controller;

import comzetyun.tiger.springbootdemo.bean.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Smexy on 2023
 * <p>
 * 控制器(Controller)：  把接收请求，处理请求，给出响应结果的类称为控制器。
 *
 * @Controller: 作用：
 * ①标识当前类是一个控制器
 * ②当前注解标识的类被容器扫描到后，会自动在容器中创建一个单例对象
 * @RestController: 作用:
 * @Controller的所有功能 + 为当前注解标识类的所有方法默认添加 @ResponseBody
 * <p>
 * ---------------------------------
 * 容器如何扫描：
 * 程序一启动，容器会自动扫描 主启动类的当前包及其子包
 */
//@Controller
@RestController
public class HelloController {

    /*
            处理方法必须要有返回值。

            @RequestMapping: 将程序的方法和url进行绑定。不在乎请求方式。
                        value: 编写要处理的url。 只写端口号后的路径。

           @ResponseBody: 将方法的返回值，作为响应体，回复给请求的发送方。
     */
    //@ResponseBody
    @RequestMapping(value = "/hello")
    public Object handle() {
        //编写方法处理逻辑
        System.out.println("收到了请求，已经处理完毕...");
        return "ok";
    }

    /*
        回复的内容类型分为两类：
                字面量：   从字面上就可以看出变量值的变量。
                            基本数据类型及包装类及字符串。
                            int i = 0;
                            String a = "hello";

                            变量不做任何处理。

                非字面量：  Employee
                            springboot自动将非字面量转换为json字符串，回复给请求方。
     */
    //@ResponseBody
    @RequestMapping(value = "/hi")
    public Object handle1() {
        //编写方法处理逻辑
        System.out.println("收到了hi请求，已经处理完毕...");

        return new Employee(1, "zhangsan", "male", "abc");
    }

    /*
        普通的参数：    k=v&k1=v1...

        只需要在程序方法的参数列表中声明和参数名名字一致的形参即可接收普通参数。类型兼容即可。
     */
    @RequestMapping(value = "/sendCommonParam")
    public Object handleCommonParam(String name, Integer age) {
        //编写方法处理逻辑
        System.out.println("收到了普通参数请求, name:" + name + ",age:" + age);

        return "ok";
    }

    /*
        json格式的请求参数:  { k:v,k1:v1  }

        需要在方法的形参中声明Bean 或 Map类型的参数接收，还需要在参数前添加@RequestBody
                Bean：  bean中必须要有和json中的属性名一致的属性，才能接收对应的值
                Map：   json中的属性以key的存在

        @RequestBody: 从请求体中获取JSON格式的参数，赋值给指定的变量。

     */
    @RequestMapping(value = "/sendJsonParam")
    public Object handleJsonParam(
            // @RequestBody Employee employee
            @RequestBody Map<String, Object> employee
    ) {
        //编写方法处理逻辑
        System.out.println("收到了普通参数请求, emp:" + employee);

        return "ok";
    }


}
