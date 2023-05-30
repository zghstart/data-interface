package comzetyun.tiger.springbootdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Smexy on 2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee
{
    private Integer id;
    private String lastName;
    private String gender;
    private String email;
}
