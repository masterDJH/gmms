package cn.djh.gmms.query;

import cn.djh.gmms.domain.Role;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * (Role)表查询类
 *
 * @author masterDJ
 * @since 2020-01-21 11:33:03
 */
// 设置针对Role实体类的特殊字段
public class RoleQuery extends BaseQuery {
    
	private String roleName;
	
    @Override
	public Specification createSpec() {
		Specification<Role> specification = Specifications.<Role>and()
				//如果还有其它字段,就再加上
				.like(StringUtils.isNoneBlank(roleName), "roleName", "%" + roleName + "%")
				.build();
		//返回查询规则
		return specification;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}