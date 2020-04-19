package cn.djh.gmms.web.shiro;

import cn.djh.gmms.domain.Permission;
import cn.djh.gmms.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限拦截Map映射对象的工厂类,用于创建权限拦截Map映射对象
 */
public class ShiroFilterMapFactory {

	@Autowired
	private IPermissionService permissionService;
	/**
	 * 改动后要重新启动tomcat
	 *	不能使用HashMap,因为HashMap无序,而这些请求的配置必须是有序的
	 *	所以使用LinkedHashMap,因为LinkedHashMap有序
	 */
	public Map<String, String> creatShiroFilterMap(){

		// 这些请求的配置有顺序,拦截所有请求的配置必须放在最后
		Map<String, String> map = new LinkedHashMap<String, String>();

		// 放行路径
		map.put("/login", "anon");
		map.put("/randomCode/**","anon");// 放行验证码
		map.put("/forget/**","anon");// 放行访问忘记密码页面
		map.put("/sendEmailCode/**","anon");// 放行发送邮件验证码
		map.put("/resetPW/**","anon");// 放行重置密码

		// 放行静态资源
		map.put("*.js","anon");
		map.put("*.css","anon");
		map.put("/css/**","anon");
		map.put("/js/**","anon");
		map.put("/easyui/**","anon");
		map.put("/images/**","anon");
		map.put("/validatebox/**","anon");

		// 将数据库中所有需要拦截的权限放入map中
		// 当用户有对应权限,才放行用户的这个请求
		List<Permission> permissions = permissionService.findAll();
		for (Permission p:permissions ) {
			String url = p.getPerUrl(); // 资源
			String sn = p.getSn(); // 权限
//			map.put(url, "perms["+sn+"]"); //放入权限、资源,使用shiro自带的默认权限过滤器
			map.put(url, "GmmsPerms["+sn+"]"); //放入权限、资源,告诉shiro使用自己的自定义权限过滤器
		}

		// 未登录,拦截所有的请求,必须放在最后配置
		map.put("/**", "authc");

		// 返回Map映射数据
		return map;
	}
}
