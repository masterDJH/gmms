package cn.djh.gmms.query;

import java.util.Date;
import cn.djh.gmms.domain.PayDetails;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * (Paydetails)表查询类
 *
 * @author masterDJ
 * @since 2020-03-06 22:31:06
 */
// 设置针对Paydetails实体类的特殊字段
public class PaydetailsQuery extends BaseQuery {
    
	private String name;
	
    @Override
	public Specification createSpec() {
		Specification<PayDetails> specification = Specifications.<PayDetails>and()
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