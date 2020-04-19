package cn.djh.gmms.query;

import cn.djh.gmms.domain.Menu;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * (Menu)表查询类
 *
 * @author masterDJ
 * @since 2020-01-20 10:55:30
 */
// 设置针对Menu实体类的特殊字段
public class MenuQuery extends BaseQuery {
    
	private String name;
	
    @Override
	public Specification createSpec() {
		Specification<Menu> specification = Specifications.<Menu>and()
				//如果还有其它字段,就再加上
				.like(StringUtils.isNoneBlank(name), "name", "%" + name + "%")
				.build();
		//返回查询规则
		return specification;
	}
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}