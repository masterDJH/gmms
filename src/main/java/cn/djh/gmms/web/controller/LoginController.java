package cn.djh.gmms.web.controller;

import cn.djh.gmms.Exception.RepetLoginException;
import cn.djh.gmms.service.IEmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// 登录控制器
@Controller
public class LoginController {

	@Autowired
	private IEmployeeService employeeService;

	/**
	 * 跳转到登录页面
	 * RESTful风格,一个请求（请求路径、请求方式get\post）对应一个资源
	 * 可以用 请求方式get\post确定一个具体的请求
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String Login(){
		return "login";
	}

	/**
	 * 开始登陆
	 * @param username
	 * @param password
	 * @param validatecode
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public Map Login(String username, String password ,String validatecode, HttpServletRequest req){
		Map map = new HashMap();
		Object randomCode = req.getSession().getAttribute("RANDOMCODE_IN_SESSION");//获取到验证码后
		req.getSession().removeAttribute("RANDOMCODE_IN_SESSION");//获取到验证码后，立即删除session中的验证码，防止用户多次提交数据

		if(randomCode!=null && validatecode.equalsIgnoreCase(randomCode.toString())) {//验证码输入正确，不分大小写
			// 直接拿到当前用户（这个用户可能是游客）
			Subject subject = SecurityUtils.getSubject();
			// 登录
			try {
				// 获取令牌
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				/**放入令牌并登录,判断用户是否正确;
				 如果正确,用户登录成功
				 如果错误,抛出异常（异常类型不确定,要看用户登录数据的情况）*/
				subject.login(token);
				map.put("success", true);
				map.put("mes", "登录成功");
			} catch (UnknownAccountException e) {
				map.put("success", false);
				map.put("mes", "用户名错误");
			} catch (IncorrectCredentialsException e){
				map.put("success", false);
				map.put("mes", "用户名或密码错误");
			} catch (RepetLoginException e){// 单点登录
				map.put("success", false);
				map.put("mes", "该账户已登录，请勿重复登录");
			} catch (AuthenticationException e){
				e.printStackTrace();
				map.put("success", false);
				map.put("mes", "登录异常");
			}finally {
				// 返回登录结果
				return map;
			}
		}else {
			map.put("success", false);
			map.put("mes", "验证码错误");
		}
		return map;
	}

	/**
	 * 注销用户
	 * @return
	 */
    @RequestMapping("/logout")
    public String Logout(){
        // 直接拿到当前用户（这个用户可能是游客）
		Subject subject = SecurityUtils.getSubject();
		subject.logout();

        return "login";
    }
}
