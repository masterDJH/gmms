package cn.djh.gmms.web.controller;



import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.query.MemberQuery;
import cn.djh.gmms.domain.Member;
import cn.djh.gmms.service.IMemberService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * (Member)表控制层
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:55
 */
@Controller
@RequestMapping("member")
public class MemberController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IMemberService memberService;

    /**
	 * 跳转至MemberController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "member/index";
	}

	/**
	 * 普通查询Member数据
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Member> findAll() {
		return memberService.findAll();
	}

	/**
	 * 根据当前用户角色查询Member数据
	 *
	 * @return
	 */
	@RequestMapping("/findMembers")
	@ResponseBody
	public List<Member> findMembers() {
		// 先查询所有会员类型数据
		List<Member> members = memberService.findAll();
		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 如果当前登录用户是管理员，页面上就去除"管理员"和"超级管理员"数据，防止管理员修改其他管理员信息
		if (current.getMember().getMemberName().equals("管理员")){
			Iterator<Member> iterator = members.iterator();
			while (iterator.hasNext()){
				Member member = iterator.next();
				if (member.getMemberName().contains("管理员")){
					iterator.remove();
				}
			}
		}
		return members;
	}

	/**
	 * 分页查询Member数据
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public UIPage queryPage(MemberQuery query) {
		// 按照查询条件,查询分页数据
		Page<Member> pageList = memberService.queryPage(query);
		// 去除“超级管理员”和“管理员”类型，防止被修改
		Iterator<Member> iterator = pageList.iterator();
		while (iterator.hasNext()){
			Member member = iterator.next();
			if (member.getMemberName().contains("管理员")){
				iterator.remove();
			}
		}
		return new UIPage(pageList);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(Long id) {
		Map map = new HashMap();
		try {
			// 删除 Member 对象
			memberService.delete(id);
			// 查询该对象,确认该对象是否还存在
			Member one = memberService.findOne(id);
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
	@ModelAttribute("updateMember")
	public Member beforeUpdate(Long id,String cmd){
		if(id!=null && "update".equals(cmd)){// id非空且cmd为"update"时,获取数据库里的原数据
			Member member = memberService.findOne(id);
			/* 把关联的Permission对象置空,解决 n-to-n 问题
			* */
			return member;
		}
		return  null;
	}

	/**
	 * 添加 Member对象
	 * @param member
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map save(Member member){
		return saveOrUpdate(member);
	}

    /**
     * 查询是否存在同名会员等级
     * @param memberName
     * @return
     */
	@RequestMapping("/findSameMemberNameNum")
	@ResponseBody
	public Boolean findSameMemberNameNum(String memberName){
        Integer sameGradeNum = memberService.findSameMemberNameNum(memberName);
        // 同名会员等级数大于 0 ,返回true;小于 0 ,返回false
        if (sameGradeNum > 0) {
            return true;
        } else {
            return false;
        }
	}
	/**
     * 查询是否存在同等级会员等级
     * @param memberGrade
     * @return
     */
	@RequestMapping("/findSameGradeNum")
	@ResponseBody
	public Boolean findSameGradeNum(Integer memberGrade){
        Integer sameGradeNum = memberService.findSameGradeNum(memberGrade);
        // 同等级会员等级数大于 0 ,返回true;小于 0 ,返回false
        if (sameGradeNum > 0) {
            return true;
        } else {
            return false;
        }
	}

	/**
	 * 修改 Member对象
	 * @param member
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateMember")注入值给member*/
	public Map update(@ModelAttribute("updateMember") Member member){
		/* member先由@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到member对象中的相应字段中
			即member对象会接收 2 次值
		 */
		Map map = saveOrUpdate(member);
		return map;
	}


	/**
	 * 添加 或 修改Member对象,不加入controller映射
	 * @param member
	 * @return
	 */
	public Map saveOrUpdate(Member member) {
//		if(member.getDepartment().getId()==null){
//			// Department对象的id为空时,将Department对象置空
//			member.setDepartment(null);
//		}
		Map map = new HashMap();
		try {
			// 执行添加或修改数据
			memberService.save(member);
			map.put("success", true);
			map.put("mes", "添加成功");
		} catch (Exception e){
			map.put("success", false);
			map.put("mes", e.getMessage());
		}
		return map;
	}
}