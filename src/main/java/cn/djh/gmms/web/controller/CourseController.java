package cn.djh.gmms.web.controller;



import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.common.UnsubscribeConstant;
import cn.djh.gmms.domain.Course;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.domain.vo.OrderCourseVO;
import cn.djh.gmms.query.CourseQuery;
import cn.djh.gmms.query.ScoreDetailsQuery;
import cn.djh.gmms.service.ICourseService;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.service.IScoreDetailsService;
import cn.djh.gmms.utils.UploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * (Course)表控制层
 *
 * @author masterDJ
 * @since 2020-01-22 14:08:50
 */
@Controller
@RequestMapping("course")
public class CourseController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private ICourseService courseService;
	@Autowired
	private IScoreDetailsService scoredetailsService;
	@Autowired
	private IEmployeeService employeeService;

	/**
     * 跳转至CourseController的index页面
     *
     * @return
     */
	@RequestMapping("/index")
	public String index() {
		return "course/index";
	}

	/**
	 * 普通查询Course数据
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Course> findAll() {
		return courseService.findAll();
	}

	/**
	 * 分页查询Course数据
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public UIPage queryPage(CourseQuery query) {
		//按照查询条件,查询分页数据
		Page<Course> pageList = courseService.queryPage(query);
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
			// 删除 Course 对象
			courseService.delete(id);
			// 查询该对象,确认该对象是否还存在
			Course one = courseService.findOne(id);
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
	@ModelAttribute("updateCourse")
	public Course beforeUpdate(Long id,String cmd){
		if(id!=null && "update".equals(cmd)){// id非空且cmd为"update"时,获取数据库里的原数据
			Course course = courseService.findOne(id);
			/* 把关联的Permission对象置空,解决 n-to-n 问题
			* */
			return course;
		}
		return  null;
	}

	/**
	 * 添加 Course对象
	 * @param course
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map save(Course course,HttpServletRequest req){
        // 获取课程图片的真实存储路径
        String realPath = req.getSession().getServletContext().getRealPath("/");//根据相对路径，获取上传的真实路径
        // 上传图片到服务器,返回上传成功后的图片名
        String uploadImgName = UploadUtil.uploadFile(course.getCourseImg(), realPath + "/upload/course");
        // 设置课程图片的名称（拼接相对路径）,以存储到数据库中
        course.setPic("/upload/course/"+uploadImgName);
		return saveOrUpdate(course);
	}

	/**
	 * 修改 Course对象
	 * @param course
	 */
	@RequestMapping("/update")
	@ResponseBody
	/*@ModelAttribute("updateCourse")注入值给course*/
	public Map update(@ModelAttribute("updateCourse") Course course, HttpServletRequest req){
		// 获取课程图片的真实存储路径
		String realPath = req.getSession().getServletContext().getRealPath("/");//根据相对路径，获取上传的真实路径

		// 上传图片到服务器,返回上传成功后的图片名
        String uploadImgName = UploadUtil.uploadFile(course.getCourseImg(), realPath + "/upload/course");
        // 设置课程图片的名称（拼接相对路径）,以存储到数据库中
        course.setPic("/upload/course/"+uploadImgName);

		/* course先由@ModelAttribute对应方法的返回值,即获取到了数据库原数据;
			再会接收到URL请求对应的添加页面上要修改的数据,并接收数据时,就相当于将数据设置到course对象中的相应字段中
			即course对象会接收 2 次值
		 */
		Map map = saveOrUpdate(course);
		return map;
	}


	/**
	 * 添加 或 修改Course对象,不加入controller映射
	 * @param course
	 * @return
	 */
	public Map saveOrUpdate(Course course) {
//		if(course.getDepartment().getId()==null){
//			// Department对象的id为空时,将Department对象置空
//			course.setDepartment(null);
//		}
		Map map = new HashMap();
		try {
			// 执行添加或修改数据
			courseService.save(course);
			map.put("success", true);
			map.put("mes", "添加成功");
		} catch (Exception e){
			map.put("success", false);
			map.put("mes", e.getMessage());
		}
		return map;
	}

	/**
	 * 跳转至 “我的课程” 的mycourse页面
	 *
	 * @return
	 */
	@RequestMapping("/mycourse")
	public String mycourse() {
		return "course/mycourse";
	}

	/**
	 * 分页查询当前登录用户已订购的Course数据(在积分明细表中查询)
	 * @return
	 */
	@RequestMapping("/mypage")
	@ResponseBody
	public UIPage myPage(ScoreDetailsQuery query) {
		// 获取当前登录用户
        Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
        // 设置当前登录用户的id到查询条件对象中
        query.setEmployee_id(current.getId());

		List<Course> pageList = null;
		// 统计当前用户的总课程订购数
		Long count = courseService.countMyPage(current.getId());
		// 根据查询条件有无,查询分页数据
		if(!StringUtils.isNotBlank(query.getCourseName())){
			pageList = courseService.myPage(query);
		}else {
			pageList = courseService.queryMyPage(query);
		}
		// 返回一个兼容分页数据格式的对象
        UIPage page = new UIPage(pageList, count);
		return page;
	}

	/**
	 * 退订已订购的课程
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/unsubscribe")
	@ResponseBody
	public Map unsubscribe(Long courseId) {
		// 获取当前登录用户
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		Map map = new HashMap();
		try {
		    // 查询要退订的课程的积分消费记录
            ScoreDetails unsubscribeCourse = scoredetailsService.findByEmployeeIdAndCourseId(current.getId(), courseId);
            // 删除该课程的记录，即退订该课程
            scoredetailsService.delete(unsubscribeCourse.getId());
            // 计算退还的课程积分（默认退回优惠价的一半）
            BigDecimal unsubscribeScore = unsubscribeCourse.getScore().multiply(UnsubscribeConstant.Unsubscribe);
            Employee loginUser = employeeService.findOne(current.getId());
            // 计算用户的总剩余积分（以前+退还）
            loginUser.setScore(loginUser.getScore().add(unsubscribeScore));
			// 退还课程积分（默认退回优惠价的一半）
            employeeService.save(loginUser);

            map.put("success", true);
			map.put("mes", "退订成功，已退还"+unsubscribeScore.setScale(0, BigDecimal.ROUND_HALF_UP)+"积分到您的账户");
		} catch (Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("mes", e.getMessage());
		}
		return map;
	}
}