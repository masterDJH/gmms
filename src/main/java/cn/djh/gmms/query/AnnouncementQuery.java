package cn.djh.gmms.query;

import java.util.Date;
import cn.djh.gmms.domain.Announcement;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * (Announcement)表查询类
 *
 * @author masterDJ
 * @since 2020-02-26 17:57:48
 */
// 设置针对Announcement实体类的特殊字段
public class AnnouncementQuery extends BaseQuery {
    
	private String title;
	
    @Override
	public Specification createSpec() {
		Specification<Announcement> specification = Specifications.<Announcement>and()
				//如果还有其它字段,就再加上
				.like(StringUtils.isNoneBlank(title), "title", "%" + title + "%")
				.build();
		//返回查询规则
		return specification;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}