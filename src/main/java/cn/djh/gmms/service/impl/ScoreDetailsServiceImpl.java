package cn.djh.gmms.service.impl;

import java.util.List;

import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.query.ScoreDetailsQuery;
import cn.djh.gmms.repository.ScoreDetailsRepository;
import cn.djh.gmms.service.IScoreDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Scoredetails)表服务实现类
 *
 * @author masterDJ
 * @since 2020-01-26 10:12:58
 */
@Service
public class ScoreDetailsServiceImpl extends BaseServiceImpl<ScoreDetails, Long> implements IScoreDetailsService {

    @Autowired
	private ScoreDetailsRepository scoreDetailsRepository;

    /**
     * 查询有效课程（未学完的课程）
     * @param employeeId
     * @return
     */
    @Override
    public List<ScoreDetails> queryAllEffect(Long employeeId) {
        List<ScoreDetails> scoreDetails = scoreDetailsRepository.queryAllEffect(employeeId);
        return scoreDetails;
    }

    /**
     * 退订已订购的课程
     * @param employeeId
     * @param courseId
     */
    @Override
    public ScoreDetails findByEmployeeIdAndCourseId(Long employeeId, Long courseId) {
        return scoreDetailsRepository.findByEmployeeIdAndCourseId(employeeId,courseId);
    }
}