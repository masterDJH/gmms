package cn.djh.gmms.query;

import cn.djh.gmms.domain.Permission;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * (Permission)表查询类
 *
 * @author masterDJ
 * @since 2020-01-21 16:40:54
 */
// 设置针对Permission实体类的特殊字段
public class PermissionQuery extends BaseQuery {
    
	private String perName;
	
    @Override
	public Specification createSpec() {
		Specification<Permission> specification = Specifications.<Permission>and()
				//如果还有其它字段,就再加上
				.like(StringUtils.isNoneBlank(perName), "perName", "%" + perName + "%")
				.build();
		//返回查询规则
		return specification;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}
}