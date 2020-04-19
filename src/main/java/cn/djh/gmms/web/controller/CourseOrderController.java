package cn.djh.gmms.web.controller;

import cn.djh.gmms.domain.Course;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.Member;
import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.domain.vo.CourseVO;
import cn.djh.gmms.query.CourseQuery;
import cn.djh.gmms.query.ScoreDetailsQuery;
import cn.djh.gmms.service.ICourseService;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.service.IMemberService;
import cn.djh.gmms.service.IScoreDetailsService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * (Course)表控制层
 *
 * @author masterDJ
 * @since 2020-01-22 14:08:50
 */
@Controller
@RequestMapping("courseOrder")
public class CourseOrderController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IScoreDetailsService scoreDetailsService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IMemberService memberService;

    /**
     * 跳转至CourseOrderController的index页面（课程订购页面）
     *
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
        // 查询当前用户可订购的课程
        List<Course> coursesEffect = this.queryEffectCourse(new CourseQuery());

        // 组装页面显示所用的数据
        List<CourseVO> courses = new ArrayList<>();
        for (Course c: coursesEffect ) {
            courses.add(new CourseVO(c));
        }
        model.addAttribute("courses",courses);
        System.out.println("课程："+courses.toString());
        return "course/course_order";
    }

    @RequestMapping(value = "/order",method = RequestMethod.GET)
    public String order(Long id) {
        // 获取shiro当前用户
        Employee currentEmployee = (Employee) SecurityUtils.getSubject().getPrincipal();
        // 先查询数据库，确认用户是否已经订购该课程
        ScoreDetails scoreDetail = scoreDetailsService.findByEmployeeIdAndCourseId(currentEmployee.getId(), id);
        if (scoreDetail!=null){// 用户已经订购该课程，直接返回页面
            return "forward:index";
        }
        // 从数据库中查询当前用户的最新信息
        Employee employee = employeeService.findOne(currentEmployee.getId());
        // 从数据库中查询欲订课程的最新信息
        Course courseDB = courseService.findOne(id);
        // 使用备份对象，防止对原课程对象的改动，导致数据库课程数据被修改
        Course course = new Course();
        course.setId(courseDB.getId());
        course.setCourseCost(courseDB.getCourseCost());
        course.setCourseName(courseDB.getCourseName());
        course.setPic(courseDB.getPic());
        course.setCourseTime(courseDB.getCourseTime());
        course.setDescs(courseDB.getDescs());

        // 获取当前用户的会员类型
        Member member = memberService.findOne(employee.getMember().getId());
        // 计算欲定购的费用（优惠后的费用）
        BigDecimal countScore = course.getCourseCost().multiply(new BigDecimal(employee.getMember().getDiscount())).setScale(0, BigDecimal.ROUND_HALF_UP);
        // 为备份课程对象设置优惠后的价格
        course.setCourseCost(countScore);
        // 判断当前用户的可用积分数，以确定是否可以定购当前课程
        if (course.getCourseCost().longValue()>employee.getScore().longValue()){// 当前用户积分不够购买指定课程，直接返回课程订购页面
            return "forward:index";
        }
        // 扣减用户积分
        employee.setScore(employee.getScore().subtract(course.getCourseCost()));
        // 保存用户数据
        employeeService.save(employee);
        // 订购完成，写入积分明细表，以记录当前用户的已订购课程
        ScoreDetails scoreDetails = new ScoreDetails();
        scoreDetails.setEmployee(employee);
        scoreDetails.setCourse(course);
        scoreDetails.setScDate(new Date());// 订购时间使用系统时间
        scoreDetails.setScore(countScore);
        scoreDetailsService.save(scoreDetails);
        // 订购完成，返回课程订购页面
        return "forward:index";
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(String courseName, Model model) {
        Map map = new HashMap();
        CourseQuery query = new CourseQuery();
        // 设置查询条件
        query.setCourseName(courseName);
        // 查询当前用户可订购的课程（不含当前用户已订购的课程）
        List<Course> coursesBookable = this.queryEffectCourse(query);

        // 组装页面显示所用的数据
        List<CourseVO> courses = new ArrayList<>();
        for (Course c: coursesBookable ) {
            courses.add(new CourseVO(c));
        }
        // 设置可预订课程到model模型中，以在页面渲染
        model.addAttribute("courses",courses);
        // 回显课程搜索条件
        model.addAttribute("search",courseName);
        return "course/course_order";
    }

    /**
     * 查询当前用户可订购的课程（含优惠转换）
     * @param query
     * @return
     */
    private List<Course> queryEffectCourse(CourseQuery query){
        // 创建当前用户的可订购课程（不直接在courses上修改，防止改动到数据库）
        List<Course> coursesBookable = new ArrayList<>();
        // 查询所有课程
        List<Course> courses = courseService.queryAll(query);
        // 获取当前用户
        Employee currentEmployee = (Employee) SecurityUtils.getSubject().getPrincipal();
        // 从数据库中查询当前用户的最新信息
        Employee employee = employeeService.findOne(currentEmployee.getId());
        // 获取当前用户的已订购的有效期内课程
        ScoreDetailsQuery scoreQuery = new ScoreDetailsQuery();
        scoreQuery.setEmployee_id(employee.getId());
        List<ScoreDetails> scoreDetails = scoreDetailsService.queryAllEffect(employee.getId());

        // 从所有可订购课程集合中寻找当前用户可以的订购课程（即剔除当前用户已订购的课程），获得当前用户的剩余可订购课程
        boolean mark = true;
        for (int j=0;j<courses.size();j++){
            mark=true;// 默认当前course可以加入coursesBookable
            for (int i=0;i<scoreDetails.size();i++){
                if (scoreDetails.get(i).getCourse().getId().equals(courses.get(j).getId())){
                    mark=false;// 当前course不可加入可订购课程
                }
            }
            if (mark){
                coursesBookable.add(courses.get(j));// 加入可订购课程
            }
        }

        // 计算销售价（并进行四舍五入）
        for (Course c: coursesBookable ) {
            c.setCourseCost(c.getCourseCost().multiply(new BigDecimal(employee.getMember().getDiscount())).setScale(0, BigDecimal.ROUND_HALF_UP));
        }
        return coursesBookable;
    }
}