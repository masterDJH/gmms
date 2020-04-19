package cn.djh.gmms.service;

import cn.djh.gmms.domain.Course;
import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.domain.vo.OrderCourseVO;
import cn.djh.gmms.query.CourseQuery;
import cn.djh.gmms.query.ScoreDetailsQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * (Course)表服务接口
 *
 * @author masterDJ
 * @since 2020-01-22 14:05:02
 */
public interface ICourseService extends IBaseService<Course,Long> {

    List<Course> myPage(ScoreDetailsQuery query);

    List<Course> queryMyPage(ScoreDetailsQuery query);

    Long countMyPage(Long id);

    Course findByCourseName(String courseName);
}