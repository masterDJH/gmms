package cn.djh.gmms.query;

import cn.djh.gmms.domain.Course;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/**
 * (Course)表查询类
 *
 * @author masterDJ
 * @since 2020-01-22 14:05:02
 */
// 设置针对Course实体类的特殊字段
public class CourseQuery extends BaseQuery {
    
	private String courseName;
	private BigDecimal courseCost;

    @Override
	public Specification createSpec() {
		Specification<Course> specification = Specifications.<Course>and()
				//如果还有其它字段,就再加上
				.like(StringUtils.isNoneBlank(courseName), "courseName", "%" + courseName + "%")
				.eq(courseCost!=null,"courseCost",courseCost)
				.build();
		//返回查询规则
		return specification;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public BigDecimal getCourseCost() {
		return courseCost;
	}

	public void setCourseCost(BigDecimal courseCost) {
		this.courseCost = courseCost;
	}
}