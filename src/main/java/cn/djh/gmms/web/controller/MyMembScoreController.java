package cn.djh.gmms.web.controller;


import cn.djh.gmms.common.ScoreConstant;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.Member;
import cn.djh.gmms.domain.PayDetails;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.service.IMemberService;
import cn.djh.gmms.service.IPaydetailsService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * (MyInfo)表控制层 个人信息管理
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:55
 */
@Controller
@RequestMapping("mymembscore")
public class MyMembScoreController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IEmployeeService employeeService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IPaydetailsService paydetailsService;

    /**
	 * 跳转至ScoreController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "mymembscore/index";
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

	/**
	 * 根据当前用户角色查询Member数据
	 *
	 * @return
	 */
	@RequestMapping("/findMembers")
	@ResponseBody
	public List<Member> findMembers() {
		// 先按grade升序查询所有会员类型数据
		List<Member> members = memberService.findOrderByMemberGrade();

		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 获取当前登录用户的会员等级grade
		Integer memberGrade = employeeService.findOne(current.getId()).getMember().getMemberGrade();

		// 遍历所有会员类型数据
		Iterator<Member> iterator = members.iterator();
		// 准备一个空会员类型集合
		List<Member> upGradeMembers = new ArrayList<Member>();
		while (iterator.hasNext()){
			Member member = iterator.next();
			if (member.getMemberGrade()!=null && member.getMemberGrade()>=memberGrade){// 选取高于当前会员等级且grade不为null(不为管理员)的会员类型
				upGradeMembers.add(member);// 放入会员类型集合中
			}
		}
		return upGradeMembers;
	}

	/**
	 * 升级会员
	 * @param employee
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateEmployee")注入值给employee*/
	public Map update(Employee employee){
		Map map = new HashMap();// 准备空的操作结果

		// 先按grade升序查询所有会员类型数据
		List<Member> members = memberService.findOrderByMemberGrade();
		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 获取当前登录充值用户信息
		Employee employeeDB = employeeService.findOne(current.getId());

		// 获取当前登录用户想要的升级会员类型的数据
		Member targetMember = memberService.findOne(employee.getMember().getId());
		Integer memberGrade = targetMember.getMemberGrade();
		// 判断欲升级会员等级是否同当前会员等级相同
		if (employeeDB.getMember().getMemberGrade() == memberGrade){// 已经是当前会员等级了
			map.put("success", false);
			map.put("mes", "您已经是"+employeeDB.getMember().getMemberName());
			return map;
		}

		// 遍历所有会员类型数据,计算升级会员所耗积分
		Iterator<Member> iterator = members.iterator();
		// 准备一个空的升级所耗积分总和
		BigDecimal upgradeScoreSum = new BigDecimal(0);
		while (iterator.hasNext()){
			Member member = iterator.next();
			if (member.getMemberGrade()!=null){// 统计grade非空（非管理员类型）、大于当前会员等级且小于等于目标会员等级的升级所耗积分总和
				if (member.getMemberGrade() > employeeDB.getMember().getMemberGrade() && member.getMemberGrade() <= memberGrade){
					// 累加升级会员所耗积分
					upgradeScoreSum = upgradeScoreSum.add(member.getMemberCost());
				}
			}
		}

		// 修改用户的会员等级
		employeeDB.setMember(targetMember);
		// 扣除升级会员所耗用积分
		employeeDB.setScore(employeeDB.getScore().subtract(upgradeScoreSum));
		try {
			// 保存用户信息
			employeeService.save(employeeDB);
			map.put("success", true);
			map.put("mes", "共消费 "+upgradeScoreSum+" 积分");
		}  catch (Exception e){// 其它异常
			map.put("success", false);
			map.put("mes", "");
		}
		// 保存充值会员消费记录
		PayDetails payDetails = new PayDetails();
		payDetails.setCost(upgradeScoreSum);
		payDetails.setPayDate(new Date());
		payDetails.setEmployee(employeeDB);
		payDetails.setMember(targetMember);
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
			map.put("success", false);
			map.put("mes", "");
		}
		return map;
	}
}