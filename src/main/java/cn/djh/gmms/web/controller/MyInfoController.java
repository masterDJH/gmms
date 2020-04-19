package cn.djh.gmms.web.controller;


import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.vo.EmployeeVO;
import cn.djh.gmms.query.EmployeeQuery;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.utils.MD5Util;
import cn.djh.gmms.utils.UploadUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (MyInfo)表控制层 个人信息管理
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:55
 */
@Controller
@RequestMapping("myinfo")
public class MyInfoController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IEmployeeService employeeService;

    /**
	 * 跳转至ScoreController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "myinfo/index";
	}

	/**
	 * 查询当前登录用户数据
	 *
	 * @return
	 */
	@RequestMapping("/findUser")
	@ResponseBody
	public Map findUser() {
		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 当前登录用户的最新信息
		Employee user = employeeService.findOne(current.getId());
		Map map = new HashMap();
		map.put("user",user);
		return map;
	}

	// 在执行@RequestMapping映射的方法前,都要先执行此方法
	@ModelAttribute("updateEmployee")
	public Employee beforeUpdate(Long id,String cmd){
		if(id!=null && "update".equals(cmd)){// id非空且cmd为"update"时,获取数据库里的原数据
			Employee employee = employeeService.findOne(id);
			/* 把关联的Permission对象置空,解决 n-to-n 问题
			* */
			return employee;
		}
		return  null;
	}

	/**
	 * 修改 Employee对象
	 * @param employee
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateEmployee")注入值给employee*/
	public Map update(@ModelAttribute("updateEmployee") Employee employee, HttpServletRequest req){
		if(employee.getHeadImage()!=null&&!employee.getHeadImage().isEmpty()){// 头像的复杂表单为空或表单值为空,不上传头像
			// 获取头像图片的真实存储路径
			String realPath = req.getSession().getServletContext().getRealPath("/");//根据相对路径，获取上传的真实路径

			// 上传头像图片到服务器,返回上传成功后的头像图片名
			String uploadImgName = UploadUtil.uploadFile(employee.getHeadImage(), realPath + "/upload/head");
			// 设置课程图片的名称（拼接相对路径）,以存储到数据库中
			employee.setHeadImg("/upload/head/"+uploadImgName);
		}

		/* employee@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到employee对象中的相应字段中
			employee 2 次值
		 */
		Map map = saveOrUpdate(employee);
		return map;
	}

	/**
	 * 重置密码
	 * @param employeeVo
	 * @return
	 */
	@RequestMapping("/updatePass")
	@ResponseBody
	public Map updatePass(EmployeeVO employeeVo){
		Map map = new HashMap();
		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 当前登录用户的最新信息
		Employee employee = employeeService.findOne(current.getId());
		// 密码试加密，并验证
		if (MD5Util.createMD5Str(employeeVo.getPassword()).equals(employee.getPassword())){// 输入密码等于原密码
			// 密码加密
			employee.setPassword(MD5Util.createMD5Str(employeeVo.getNewPassword()));
			map = saveOrUpdate(employee);
		}else {// 输入密码不等于原密码
			map.put("success", false);
			map.put("mes", "原密码错误");
		}
		return map;
	}


	/**
	 * 修改Employee对象的信息,不加入controller映射
	 * @param employee
	 * @return
	 */
	public Map saveOrUpdate(Employee employee) {
		Map map = new HashMap();
		try {
			// 执行修改数据
			employeeService.save(employee);
			map.put("success", true);
			map.put("mes", "修改成功");
		}  catch (Exception e){// 其它异常
			map.put("success", false);
			map.put("mes", "");
		}
		return map;
	}
}