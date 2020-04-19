package cn.djh.gmms.web.controller;


import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.query.EmployeeQuery;
import cn.djh.gmms.service.IEmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.RollbackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * (Score)表控制层 积分管理
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:55
 */
@Controller
@RequestMapping("score")
public class ScoreController extends BaseController {
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
		return "score/index";
	}

	/**
	 * 普通查询Employee数据
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Employee> findAll() {
		return employeeService.findAll();
	}


	/**
	 * 分页查询会员用户积分数据
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public UIPage queryPage(EmployeeQuery query) {

		// 按照查询条件,查询会员用户积分分页数据
		List<Employee> pageList = employeeService.queryScorePage(query);
		// 统计会员用户积分记录总数
		Long count = employeeService.countScorePage(query);

		return new UIPage(pageList,count);
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
	public Map update(@ModelAttribute("updateEmployee") Employee employee){
		/* employee@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到employee对象中的相应字段中
			employee 2 次值
		 */
		Map map = saveOrUpdate(employee);
		return map;
	}


	/**
	 * 修改Employee对象的积分,不加入controller映射
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
		} catch (TransactionSystemException e){// 返回提示：积分不能低于0分
			map.put("success", false);
			map.put("mes", "积分不能低于0分");
		} catch (Exception e){// 其它异常
			map.put("success", false);
			map.put("mes", "");
		}
		return map;
	}
}