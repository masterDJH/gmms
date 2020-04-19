package cn.djh.gmms.repository;

import java.util.Date;
import java.util.List;

import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.query.ScoreDetailsQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * (Scoredetails)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-01-26 10:12:58
 */
public interface ScoreDetailsRepository extends BaseRepository<ScoreDetails, Long> {

    // 查询有效课程（未学完的课程）
    @Query(nativeQuery = true,value = "SELECT * FROM scoredetails s LEFT JOIN course c on s.course_id=c.id WHERE employee_id = ?1 AND DATE_ADD(s.scDate, INTERVAL c.courseTime MONTH)>curdate()")
    List<ScoreDetails> queryAllEffect(Long employeeId);

    // 查询指定用户的指定课程的积分消费记录
    @Query("select s from ScoreDetails s join s.employee e join s.course c where e.id = ?1 and c.id = ?2")
    ScoreDetails findByEmployeeIdAndCourseId(Long employeeId, Long courseId);
}