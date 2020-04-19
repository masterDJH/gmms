package cn.djh.gmms.service.impl;

import java.util.Date;
import cn.djh.gmms.domain.PayDetails;
import cn.djh.gmms.repository.PaydetailsRepository;
import cn.djh.gmms.service.IPaydetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Paydetails)表服务实现类
 *
 * @author masterDJ
 * @since 2020-03-06 22:31:06
 */
@Service
public class PaydetailsServiceImpl extends BaseServiceImpl<PayDetails, Long> implements IPaydetailsService {

    @Autowired
	private PaydetailsRepository paydetailsRepository;
}