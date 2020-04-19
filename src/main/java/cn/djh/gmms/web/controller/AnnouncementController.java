package cn.djh.gmms.web.controller;

import java.util.Date;
import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.query.AnnouncementQuery;
import cn.djh.gmms.domain.Announcement;
import cn.djh.gmms.service.IAnnouncementService;
import cn.djh.gmms.service.IEmployeeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Announcement)表控制层
 *
 * @author masterDJ
 * @since 2020-02-26 17:58:13
 */
@Controller
@RequestMapping("announcement")
public class AnnouncementController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IAnnouncementService announcementService;
	@Autowired
	private IEmployeeService employeeService;

	/**
     * 跳转至AnnouncementController的check页面（会员用户的公告查看页面）
     *
     * @return
     */
	@RequestMapping("/check")
	public String check() {
		return "announcement/check";
	}

	/**
	 * 跳转至AnnouncementController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "announcement/index";
	}

	/**
	 * 普通查询Announcement数据
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Announcement> findAll() {
		return announcementService.findAll();
	}

	/**
	 * 分页查询Announcement数据
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public UIPage queryPage(AnnouncementQuery query) {
		// 最开始按照id的升序排序（即按照公告发布时间的升序排序）
		query.setOrderType(true);
		//按照查询条件,查询分页数据
		Page<Announcement> pageList = announcementService.queryPage(query);
		// 返回一个兼容分页数据格式的对象
		return new UIPage(pageList);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(Long id) {
		Map map = new HashMap();
		try {
			// 删除 Announcement 对象
			announcementService.delete(id);
			// 查询该对象,确认该对象是否还存在
			Announcement one = announcementService.findOne(id);
			if(one==null){
				map.put("success", true);
				map.put("mes", "公告删除成功");
			}else {
				map.put("success", false);
				map.put("mes", "公告删除失败");
			}
		}catch (Exception e){
			map.put("success", false);
			map.put("mes", "公告删除失败");
		}
		return map;
	}

	// 在执行@RequestMapping映射的方法前,都要先执行此方法
	@ModelAttribute("updateAnnouncement")
	public Announcement beforeUpdate(Long id,String cmd){
		if(id!=null && "update".equals(cmd)){// id非空且cmd为"update"时,获取数据库里的原数据
			Announcement announcement = announcementService.findOne(id);
			/* 把关联的Permission对象置空,解决 n-to-n 问题
			* */
			return announcement;
		}
		return  null;
	}

	/**
	 * 添加 Announcement对象
	 * @param announcement
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map save(Announcement announcement){
		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 获取当前登录用户的最新信息
		Employee announcer = employeeService.findOne(current.getId());
		// 设置发布人
		announcement.setEmployee(announcer);
		return saveOrUpdate(announcement);
	}

	/**
	 * 修改 Announcement对象
	 * @param announcement
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateAnnouncement")注入值给announcement*/
	public Map update(@ModelAttribute("updateAnnouncement") Announcement announcement){
		/* announcement先由@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到announcement对象中的相应字段中
			即announcement对象会接收 2 次值
		 */
		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 获取当前登录用户的最新信息
		Employee announcer = employeeService.findOne(current.getId());
		// 设置修改人
		announcement.setEmployee(announcer);
		Map map = saveOrUpdate(announcement);
		return map;
	}


	/**
	 * 添加 或 修改Announcement对象,不加入controller映射
	 * @param announcement
	 * @return
	 */
	public Map saveOrUpdate(Announcement announcement) {
		Map map = new HashMap();
		try {
			// 执行添加或修改数据
			announcementService.save(announcement);
			map.put("success", true);
			map.put("mes", "添加成功");
		} catch (Exception e){
			map.put("success", false);
			map.put("mes", "");
		}
		return map;
	}
}