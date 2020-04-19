package cn.djh.gmms.web.controller;

import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.service.IEmployeeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {
	@Autowired
	private IEmployeeService employeeService;

	@RequestMapping("/main")
	public String main(){
		return "main";
	}

	@RequestMapping("/head")
	@ResponseBody
	public String randomCode(HttpServletRequest req, HttpServletResponse resp) {
		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 获取当前登录用户的最新信息
		Employee employee = employeeService.findOne(current.getId());
		//把图片对象以流的方式保存出去
//		try {
//			ImageIO.write(image, "jpg", resp.getOutputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return employee.getHeadImg();
	}
}
