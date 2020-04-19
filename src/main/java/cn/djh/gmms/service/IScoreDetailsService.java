package cn.djh.gmms.service;

import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.query.ScoreDetailsQuery;

import java.util.List;

/**
 * (Scoredetails)表服务接口
 *
 * @author masterDJ
 * @since 2020-01-26 10:12:58
 */
public interface IScoreDetailsService extends IBaseService<ScoreDetails,Long> {

    List<ScoreDetails> queryAllEffect(Long employeeId);

    ScoreDetails findByEmployeeIdAndCourseId(Long employeeId, Long courseId);
}