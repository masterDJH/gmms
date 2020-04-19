package cn.djh.gmms.query;

import cn.djh.gmms.domain.Member;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * (Member)表查询类
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:38
 */
// 设置针对Member实体类的特殊字段
public class MemberQuery extends BaseQuery {
    
	private String memberName;
	
    @Override
	public Specification createSpec() {
		Specification<Member> specification = Specifications.<Member>and()
				//如果还有其它字段,就再加上
				.like(StringUtils.isNoneBlank(memberName), "memberName", "%" + memberName + "%")
				.build();
		//返回查询规则
		return specification;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}