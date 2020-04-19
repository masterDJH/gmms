package cn.djh.gmms.web.controller;


import cn.djh.gmms.common.ScoreConstant;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.PayDetails;
import cn.djh.gmms.domain.vo.EmployeeVO;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.service.IPaydetailsService;
import cn.djh.gmms.utils.MD5Util;
import cn.djh.gmms.utils.UploadUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * (MyInfo)表控制层 个人信息管理
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:55
 */
@Controller
@RequestMapping("charge")
public class ChargeController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IEmployeeService employeeService;
	@Autowired
	private IPaydetailsService paydetailsService;

    /**
	 * 跳转至ScoreController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "charge/index";
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
		// 显示积分置 null ，防止充值金额框显示积分数
		user.setScore(null);
		Map map = new HashMap();
		map.put("user",user);
		return map;
	}

	/**
	 * 会员充值
	 * @param employee
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateEmployee")注入值给employee*/
	public Map update(Employee employee){
		Map map = new HashMap();
		if (employee.getScore()==null || employee.getScore().equals(new BigDecimal(0))){
			map.put("success", false);
			map.put("mes", "充值金额不能为0");
			return map;
		}

		// 获取充值用户信息
		Employee employeeDB = employeeService.findOne(employee.getId());
		// 累加用户积分（按比例兑换积分）
		employeeDB.setScore(employeeDB.getScore().add(employee.getScore().multiply(ScoreConstant.Exchange)));
		map = saveOrUpdate(employeeDB);
		// 保存充值记录
		PayDetails payDetails = new PayDetails();
		payDetails.setCost(employee.getScore());
		payDetails.setPayDate(new Date());
		payDetails.setEmployee(employeeDB);
		paydetailsService.save(payDetails);
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
			map.put("mes", "充值成功");
		}  catch (Exception e){// 其它异常
			e.printStackTrace();
			map.put("success", false);
			map.put("mes", "");
		}
		return map;
	}
}