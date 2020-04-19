package cn.djh.gmms.web.controller;

import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class ForgetController {

    @Autowired
    private IEmployeeService IEmployeeService;

    @RequestMapping(value = "/forget",method = RequestMethod.GET)
    public String Forget(String username,Model model){
        // 向忘记密码界面传入 当前用户名
        model.addAttribute("username",username);
        // 跳转到忘记密码界面
        return "forget";
    }

    /**
     * 发送邮箱验证码
     * @return
     */
    @RequestMapping(value = "/sendEmailCode",method = RequestMethod.POST)
    @ResponseBody
    public Map SendEmailCode(String username,String email,HttpServletRequest req){
        Map map = new HashMap();

        Employee user = IEmployeeService.findByUsername(username);
        if (user==null){// 不存在该用户名的用户
            map.put("success", false);
            map.put("mes", "该用户不存在!");
            return map;
        }else {
            if(!user.getEmail().equals(email)){// 该用户没有绑定这个邮箱
                map.put("success", false);
                map.put("mes", "您未绑定该邮箱!");
                return map;
            }
        }
        // 生成邮箱验证码
        String geEmailCode = UUID.randomUUID().toString().substring(0, 5);
        String message = "<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "    <meta http-equiv='X-UA-Compatible' content='ie=edge'>\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h2>欢迎使用健身房会员管理系统！</h2>\n" +
                "    <h3>您的验证码是:<span style='color:rgb(25, 136, 201)'>"+geEmailCode+"</span></h3>\n" +
                "</body>\n" +
                "</html>";
        // 发送邮箱验证码
        int result = MailUtil.sendEmail("smtp.qq.com", "623553207@qq.com", "gjdkpjgorwqdbbgd",
                "623553207@qq.com", new String[]{email}, "会员验证码", message, "text/html;charset=utf-8");

        if (result==1){
            map.put("success", true);
            map.put("mes", "邮箱验证码发送成功,请注意查收!");
        }else {
            map.put("success", false);
            map.put("mes", "邮箱验证码发送失败,请重试!");
        }
        // 把邮箱验证码放进Session中,便于重置密码时比较邮箱验证码
        req.getSession().setAttribute("EMAILCODE_IN_SESSION", geEmailCode);
        return map;
    }

    /**
     * 重置用户密码
     * @param username
     * @param validatecode
     * @param emailCode
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/resetPW",method = RequestMethod.POST)
    @ResponseBody
    public Map ResetPW(String username,String validatecode,String emailCode,String newPassword,HttpServletRequest req){
        Map map = new HashMap();
        // 重置用户密码
        Object randomCode = req.getSession().getAttribute("RANDOMCODE_IN_SESSION");
        req.getSession().removeAttribute("RANDOMCODE_IN_SESSION");//获取到随机验证码后，立即删除session中的随机验证码，防止用户多次提交数据
        Object emailCodeInSession = req.getSession().getAttribute("EMAILCODE_IN_SESSION");
        req.getSession().removeAttribute("EMAILCODE_IN_SESSION");//获取到邮箱验证码后，立即删除session中的邮箱验证码，防止用户多次提交数据

        if(randomCode!=null && validatecode.equalsIgnoreCase(randomCode.toString())) {//验证码输入正确，不分大小写
            if(emailCodeInSession!=null && emailCodeInSession.toString().equalsIgnoreCase(emailCode.toString())) {//邮箱验证码输入正确，不分大小写
                Employee user = IEmployeeService.findByUsername(username);
                user.setPassword(newPassword);
                // 重置密码
                try {
                    IEmployeeService.save(user);
                    map.put("success", true);
                    map.put("mes", "重置密码成功,请重新登录!");
                }catch (Exception e){
                    map.put("success", false);
                    map.put("mes", "重置密码失败,请重试!");
                }
            }else {
                map.put("success", false);
                map.put("mes", "邮箱验证码错误");
            }
        }else {
            map.put("success", false);
            map.put("mes", "验证码错误");
        }

//        map.put("success", true);
//        map.put("mes", "重置密码成功,请重新登录!");
        return map;
    }
}
