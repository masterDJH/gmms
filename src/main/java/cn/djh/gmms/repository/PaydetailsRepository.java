package cn.djh.gmms.repository;

import java.util.Date;
import cn.djh.gmms.domain.PayDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * (Paydetails)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-03-06 22:31:06
 */
public interface PaydetailsRepository extends BaseRepository<PayDetails, Long> {

}