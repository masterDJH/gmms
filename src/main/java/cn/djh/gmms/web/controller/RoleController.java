package cn.djh.gmms.web.controller;



import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.domain.Member;
import cn.djh.gmms.query.RoleQuery;
import cn.djh.gmms.domain.Role;
import cn.djh.gmms.service.IMemberService;
import cn.djh.gmms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Role)表控制层
 *
 * @author masterDJ
 * @since 2020-01-21 11:32:41
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IRoleService roleService;
	@Autowired
	private IMemberService memberService;

    /**
	 * 跳转至RoleController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "role/index";
	}

	/**
	 * 普通查询Role数据
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Role> findAll() {
		return roleService.findAll();
	}

	/**
	 * 分页查询Role数据
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public UIPage queryPage(RoleQuery query) {
		//按照查询条件,查询分页数据
		Page<Role> pageList = roleService.queryPage(query);
		// 另一种兼容分页数据格式
		// Map map = new HashMap();
		// map.put("total", pageList.getTotalElements());
		// map.put("rows", pageList.getContent());
		// 返回一个兼容分页数据格式的对象
		return new UIPage(pageList);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(Long id) {
		Map map = new HashMap();
		try {
		    // 查出要删除的角色类型,以获得角色名字s
            Role role = roleService.findOne(id);
            // 为统一页面效果，删除会员类型中的指定"管理员"类型
            if (role.getRoleName().contains("管理员")){
                // 查询指定名字的会员类型
                Member member = memberService.findByName(role.getRoleName());
                memberService.delete(member.getId());
            }

			// 删除 Role 对象
			roleService.delete(id);
			// 查询该对象,确认该对象是否还存在
			Role one = roleService.findOne(id);
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
	@ModelAttribute("updateRole")
	public Role beforeUpdate(Long id,String cmd){
		if(id!=null && "update".equals(cmd)){// id非空且cmd为"update"时,获取数据库里的原数据
			Role role = roleService.findOne(id);
			/* 把关联的Permission对象置空,解决 n-to-n 问题
			* */
			role.setPermissions(new ArrayList<>());
			return role;
		}
		return  null;
	}

	/**
	 * 添加 Role对象
	 * @param role
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map save(Role role){
	    // 为统一页面效果，将"管理员"类型当做特殊会员类型进行存储
		if (role.getRoleName().contains("管理员")){
			Member member = new Member();
			member.setMemberName(role.getRoleName());
			memberService.save(member);
		}
		return saveOrUpdate(role);
	}

	/**
	 * 修改 Role对象
	 * @param role
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateRole")注入值给role*/
	public Map update(@ModelAttribute("updateRole") Role role){
		/* role先由@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到role对象中的相应字段中
			即role对象会接收 2 次值
		 */
		Map map = saveOrUpdate(role);
		return map;
	}


	/**
	 * 添加 或 修改Role对象,不加入controller映射
	 * @param role
	 * @return
	 */
	public Map saveOrUpdate(Role role) {
//		if(role.getDepartment().getId()==null){
//			// Department对象的id为空时,将Department对象置空
//			role.setDepartment(null);
//		}
		Map map = new HashMap();
		try {
			// 执行添加或修改数据
			roleService.save(role);
			map.put("success", true);
			map.put("mes", "添加成功");
		} catch (Exception e){
			map.put("success", false);
			map.put("mes", e.getMessage());
		}
		return map;
	}
}