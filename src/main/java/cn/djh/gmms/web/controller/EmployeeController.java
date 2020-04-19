package cn.djh.gmms.web.controller;



import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.Member;
import cn.djh.gmms.query.EmployeeQuery;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.service.IMemberService;
import cn.djh.gmms.service.IRoleService;
import cn.djh.gmms.utils.MD5Util;
import cn.djh.gmms.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * (Employee)表控制层
 *
 * @author masterDJ
 * @since 2020-01-19 11:03:26
 */
@Controller
@RequestMapping("employee")
public class EmployeeController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IRoleService roleService;
	@Autowired
	private IMemberService memberService;

	/**
	 * 跳转至Employee的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "employee/index";
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
	 * 分页查询Employee数据
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public UIPage queryPage(EmployeeQuery query) {

		//按照查询条件,查询分页数据
		Page<Employee> pageList = employeeService.queryPage(query);
		UIPage page = new UIPage(pageList);
		// 返回一个兼容分页数据格式的对象
		return page;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(Long id) {
		Map map = new HashMap();
		try {
			// 删除 Employee 对象
            employeeService.delete(id);
			// 查询该对象,确认该对象是否还存在
			Employee one = employeeService.findOne(id);
			if(one==null){
				map.put("success", true);
				map.put("mes", "删除成功");
			}else {
				map.put("success", false);
				map.put("mes", "删除失败");
			}
		}catch (Exception e){
			map.put("success", false);
			map.put("mes", e.getMessage());
		}
		return map;
	}

	// 在执行@RequestMapping映射的方法前,都要先执行此方法
	@ModelAttribute("updateEmployee")
	public Employee beforeUpdate(Long id,String cmd){
		if(id!=null && "update".equals(cmd)){// id非空且cmd为"update"时,获取数据库里的原数据
			Employee employee = employeeService.findOne(id);
			/* 把关联的member对象置空,解决 n-to-n 问题
			* */
			employee.setMember(null);
			return employee;
		}
		return  null;
	}

	/**
	 * 添加 Employee对象
	 * @param employee
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map save(Employee employee){
		// 对新用户的密码进行加密
		employee.setPassword(MD5Util.createMD5Str(employee.getPassword()));
		// 初始化新用户的积分为0.00
		employee.setScore(new BigDecimal(0.00));
		return saveOrUpdate(employee);
	}

	/**
	 * 由管理员修改用户信息（含头像上传）
	 * @param employee
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateEmployee")注入值给employee*/
	public Map update(@ModelAttribute("updateEmployee") Employee employee){
		/* employee先由@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到employee对象中的相应字段中
			即employee对象会接收 2 次值
		 */
		Map map = saveOrUpdate(employee);
		return map;
	}

	/**
	 * 添加 或 修改Employee对象,不加入controller映射
	 * @param employee
	 * @return
	 */
	public Map saveOrUpdate(Employee employee) {
//		if(employee.getDepartment().getId()==null){
//			// Department对象的id为空时,将Department对象置空
//			employee.setDepartment(null);
//		}
		List roles = new ArrayList<>();
		// 获取对应会员类型id的会员类型数据
		Member member = memberService.findOne(employee.getMember().getId());
		// 根据会员类型名称设置对应的角色类型
		if (member.getMemberName().equals("超级管理员")){
			roles.add(roleService.findBySn("admin"));
		} else if (member.getMemberName().equals("管理员")){
			roles.add(roleService.findBySn("manager"));
		} else {
			roles.add(roleService.findBySn("member"));
		}
		// 设置角色类型
		employee.setRoles(roles);
		Map map = new HashMap();
		try {
			// 执行添加或修改数据
            employeeService.save(employee);
			map.put("success", true);
			map.put("mes", "添加成功");
		} catch (Exception e){
			map.put("success", false);
			map.put("mes", e.getMessage());
		}
		return map;
	}

    /**
     * 是否存在同名用户
     *
     * @param employee
     */
    @RequestMapping("/findSameUserNum")
    @ResponseBody
    public Boolean findSameUserNum(Employee employee) {
        Integer sameUserNum = employeeService.findSameUserNum(employee.getUsername());
        // 同名用户数大于 0 ,返回true;小于 0 ,返回false
        if (sameUserNum > 0) {
            return true;
        } else {
            return false;
        }
    }
}