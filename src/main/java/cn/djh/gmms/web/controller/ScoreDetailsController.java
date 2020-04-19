package cn.djh.gmms.web.controller;



import cn.djh.gmms.common.UIPage;
import cn.djh.gmms.query.ScoreDetailsQuery;
import cn.djh.gmms.domain.ScoreDetails;
import cn.djh.gmms.service.IScoreDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Scoredetails)表控制层
 *
 * @author masterDJ
 * @since 2020-01-26 10:12:42
 */
@Controller
@RequestMapping("scoredetails")
public class ScoreDetailsController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IScoreDetailsService scoredetailsService;

    /**
	 * 跳转至ScoredetailsController的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "scoredetails/index";
	}

}