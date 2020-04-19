package cn.djh.gmms.repository;

import cn.djh.gmms.domain.Course;
import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.domain.vo.OrderCourseVO;
import cn.djh.gmms.query.CourseQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * (Course)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-01-22 14:05:02
 */
public interface CourseRepository extends BaseRepository<Course, Long> {

    // 分页查询当前登录用户已订购的Course数据
    @Query(nativeQuery = true,value = "SELECT c.id,c.courseName,c.courseCost,c.descs,c.pic,c.courseTime FROM scoredetails s LEFT JOIN course c on s.course_id=c.id WHERE employee_id = ?1 AND DATE_ADD(s.scDate, INTERVAL c.courseTime MONTH)>curdate() limit ?2, ?3")
    List<Course> findMyCourse(Long id, Integer page, Integer pageSiza);

    // 条件分页查询当前登录用户已订购的Course数据
    @Query(nativeQuery = true,value = "SELECT c.id,c.courseName,c.courseCost,c.descs,c.pic,c.courseTime FROM scoredetails s LEFT JOIN course c on s.course_id=c.id WHERE employee_id = ?1 AND c.courseName LIKE ?2 AND DATE_ADD(s.scDate, INTERVAL c.courseTime MONTH)>curdate() limit ?3, ?4")
    List<Course> queryMyPage(Long id, String courseName, Integer page, Integer pageSiza);

    // 统计指定用户的总课程订购数
    @Query(nativeQuery = true,value = "SELECT COUNT(s.id) FROM scoredetails s LEFT JOIN course c on s.course_id=c.id WHERE employee_id = ?1 AND DATE_ADD(s.scDate, INTERVAL c.courseTime MONTH)>curdate()")
    Long countMyPage(Long id);

    // 根据课程名查询课程
    Course findByCourseName(String courseName);
}