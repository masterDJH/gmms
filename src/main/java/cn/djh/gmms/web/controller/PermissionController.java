package cn.djh.gmms.web.controller;



import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.query.PermissionQuery;
import cn.djh.gmms.domain.Permission;
import cn.djh.gmms.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Permission)表控制层
 *
 * @author masterDJ
 * @since 2020-01-21 16:41:22
 */
@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IPermissionService permissionService;

    /**
	 * 跳转至PermissionController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "permission/index";
	}

	/**
	 * 普通查询Permission数据
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Permission> findAll() {
		return permissionService.findAll();
	}

	/**
	 * 分页查询Permission数据
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public UIPage queryPage(PermissionQuery query) {
		//按照查询条件,查询分页数据
		Page<Permission> pageList = permissionService.queryPage(query);
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
			// 删除 Permission 对象
			permissionService.delete(id);
			// 查询该对象,确认该对象是否还存在
			Permission one = permissionService.findOne(id);
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
	@ModelAttribute("updatePermission")
	public Permission beforeUpdate(Long id,String cmd){
		if(id!=null && "update".equals(cmd)){// id非空且cmd为"update"时,获取数据库里的原数据
			Permission permission = permissionService.findOne(id);
			/* 把关联的Permission对象置空,解决 n-to-n 问题
			* */
			permission.setMenu(null);
			return permission;
		}
		return  null;
	}

	/**
	 * 添加 Permission对象
	 * @param permission
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map save(Permission permission){
		return saveOrUpdate(permission);
	}

	/**
	 * 修改 Permission对象
	 * @param permission
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updatePermission")注入值给permission*/
	public Map update(@ModelAttribute("updatePermission") Permission permission){
		/* permission先由@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到permission对象中的相应字段中
			即permission对象会接收 2 次值
		 */
		Map map = saveOrUpdate(permission);
		return map;
	}


	/**
	 * 添加 或 修改Permission对象,不加入controller映射
	 * @param permission
	 * @return
	 */
	public Map saveOrUpdate(Permission permission) {
		if(permission.getMenu().getId()==null){
			// Menu对象的id为空时,将Menu对象置空
			permission.setMenu(null);
		}
		Map map = new HashMap();
		try {
			// 执行添加或修改数据
			permissionService.save(permission);
			map.put("success", true);
			map.put("mes", "添加成功");
		} catch (Exception e){
			map.put("success", false);
			map.put("mes", e.getMessage());
		}
		return map;
	}
}