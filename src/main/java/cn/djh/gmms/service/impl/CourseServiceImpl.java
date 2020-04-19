package cn.djh.gmms.service.impl;

import cn.djh.gmms.domain.Course;
import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.domain.vo.OrderCourseVO;
import cn.djh.gmms.query.CourseQuery;
import cn.djh.gmms.query.ScoreDetailsQuery;
import cn.djh.gmms.repository.CourseRepository;
import cn.djh.gmms.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Course)表服务实现类
 *
 * @author masterDJ
 * @since 2020-01-22 14:05:02
 */
@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long> implements ICourseService {

    @Autowired
	private CourseRepository courseRepository;

    /**
     * 分页查询当前登录用户已订购的Course数据
     * @param query
     * @return
     */
    @Override
    public List<Course> myPage(ScoreDetailsQuery query) {
        return courseRepository.findMyCourse(query.getEmployee_id(),query.getJpaPage()*query.getPageSize(),query.getPageSize());
    }

    /**
     * （带条件）分页查询当前登录用户已订购的Course数据
     * @param query
     * @return
     */
    @Override
    public List<Course> queryMyPage(ScoreDetailsQuery query) {
        String courseName = query.getCourseName();
        return courseRepository.queryMyPage(query.getEmployee_id(),"%"+courseName+"%",query.getJpaPage(),query.getPageSize());
    }

    /**
     * 统计指定用户的总课程订购数
     * @param id
     * @return
     */
    @Override
    public Long countMyPage(Long id) {
        return courseRepository.countMyPage(id);
    }

    /**
     * 根据课程名查询课程
     * @param courseName
     * @return
     */
    @Override
    public Course findByCourseName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }
}