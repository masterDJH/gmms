package cn.djh.gmms.service.impl;

import java.util.Date;
import cn.djh.gmms.domain.Announcement;
import cn.djh.gmms.repository.AnnouncementRepository;
import cn.djh.gmms.service.IAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Announcement)表服务实现类
 *
 * @author masterDJ
 * @since 2020-02-26 17:57:48
 */
@Service
public class AnnouncementServiceImpl extends BaseServiceImpl<Announcement, Long> implements IAnnouncementService {

    @Autowired
	private AnnouncementRepository announcementRepository;
}