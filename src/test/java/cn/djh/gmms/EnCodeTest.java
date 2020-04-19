package cn.djh.gmms;

import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EnCodeTest {

    @Autowired
    private IEmployeeService employeeService;

    // 对数据库所有密码按用户名字加密
    @Test
    public void testUpdatePwd() throws Exception{
        List<Employee> list = employeeService.findAll();
        for (Employee e:list){
            // 按用户名字加密
            e.setPassword(MD5Util.createMD5Str(e.getUsername()));
            employeeService.save(e);
        }
    }
}
