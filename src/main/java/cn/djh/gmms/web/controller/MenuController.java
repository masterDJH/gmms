package cn.djh.gmms.web.controller;

import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.query.MenuQuery;
import cn.djh.gmms.domain.Menu;
import cn.djh.gmms.service.IMenuService;
import org.apache.shiro.SecurityUtils;
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
 * (Menu)表控制层
 *
 * @author masterDJ
 * @since 2020-01-20 11:12:36
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IMenuService menuService;

	// 查询所有父菜单项
	@RequestMapping("/findParenrtMenus")
	@ResponseBody
	public List<Menu> findParenrMenus(){
		List<Menu> menus = menuService.findParenrtMenus();
		return menus;
	}
	// 查询所有子菜单项
	@RequestMapping("/findAllChildrenMenus")
	@ResponseBody
	public List<Menu> findAllChildrenMenus(){
		List<Menu> menus = menuService.findAllChildrenMenus();
		return menus;
	}

	// 根据用户名查询子菜单项
	@RequestMapping("/findChildrenMenus")
	@ResponseBody
	public List<Menu> findChildrenMenus(){
		Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
		List<Menu> menus = menuService.findChildrenMenus(employee.getId());
		// 将菜单按照排序模板template进行排序
		String[] template = new String[]{"人员","系统","课程积分","导入导出","课程","我的会员","公告","个人信息"};
		List<Menu> sortedMenus = this.sort(menus, template);
		return sortedMenus;
	}

	/**
	 * 菜单排序（按照事先给定的排序模板进行排序）
	 * @param menus
	 * @param template
	 * @return
	 */
	private List<Menu> sort(List<Menu> menus,String[] template){
		List<Menu> sortedMenus = new ArrayList<>();
		for (int i=0;i<template.length;i++){
			for (int j=0;j<menus.size();j++){
				if (template[i].equals(menus.get(j).getName())){
					sortedMenus.add(menus.get(j));
				}
			}
		}
		return sortedMenus;
	}

	/**
	 * 跳转至MenuController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "menu/index";
	}

	/**
	 * 普通查询Menu数据
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Menu> findAll() {
		return menuService.findAll();
	}

	/**
	 * 分页查询Menu数据
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public UIPage queryPage(MenuQuery query) {
		//按照查询条件,查询分页数据
		Page<Menu> pageList = menuService.queryPage(query);
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
			// 删除 Menu 对象
			menuService.delete(id);
			// 查询该对象,确认该对象是否还存在
			Menu one = menuService.findOne(id);
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
	@ModelAttribute("updateMenu")
	public Menu beforeUpdate(Long id,String cmd){
		if(id!=null && "update".equals(cmd)){// id非空且cmd为"update"时,获取数据库里的原数据
			Menu menu = menuService.findOne(id);
			/* 把关联的Permission对象置空,解决 n-to-n 问题
			* */
			return menu;
		}
		return  null;
	}

	/**
	 * 添加 Menu对象
	 * @param menu
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map save(Menu menu){
		return saveOrUpdate(menu);
	}

	/**
	 * 修改 Menu对象
	 * @param menu
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateMenu")注入值给menu*/
	public Map update(@ModelAttribute("updateMenu") Menu menu){
		/* menu先由@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到menu对象中的相应字段中
			即menu对象会接收 2 次值
		 */
		Map map = saveOrUpdate(menu);
		return map;
	}


	/**
	 * 添加 或 修改Menu对象,不加入controller映射
	 * @param menu
	 * @return
	 */
	public Map saveOrUpdate(Menu menu) {
//		if(menu.getDepartment().getId()==null){
//			// Department对象的id为空时,将Department对象置空
//			menu.setDepartment(null);
//		}
		Map map = new HashMap();
		try {
			// 执行添加或修改数据
			menuService.save(menu);
			map.put("success", true);
			map.put("mes", "添加成功");
		} catch (Exception e){
			map.put("success", false);
			map.put("mes", e.getMessage());
		}
		return map;
	}
}