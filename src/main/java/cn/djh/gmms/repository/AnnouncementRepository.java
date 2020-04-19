package cn.djh.gmms.repository;

import java.util.Date;
import cn.djh.gmms.domain.Announcement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * (Announcement)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-02-26 17:57:48
 */
public interface AnnouncementRepository extends BaseRepository<Announcement, Long> {

}