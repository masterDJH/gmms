package cn.djh.gmms.query;

import java.util.Date;
import cn.djh.gmms.domain.ScoreDetails;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * (Scoredetails)表查询类
 *
 * @author masterDJ
 * @since 2020-01-26 10:12:58
 */
// 设置针对Scoredetails实体类的查询字段
public class ScoreDetailsQuery extends BaseQuery {
    
	private Long employee_id;
	private String courseName;
	
    @Override
	public Specification createSpec() {
		Specification<ScoreDetails> specification = Specifications.<ScoreDetails>and()
				//如果还有其它字段,就再加上
				.eq(employee_id!=null,"employee_id",employee_id)
				.build();
		//返回查询规则
		return specification;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}