package cn.djh.gmms.query;

import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.Member;
import cn.djh.gmms.service.IMemberService;
import com.github.wenhao.jpa.Sorts;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * (Employee)表查询类
 *
 * @author masterDJ
 * @since 2020-01-19 11:03:02
 */
// 设置针对Employee实体类的特殊字段
public class EmployeeQuery extends BaseQuery {

	// 用户名
	private String username;
	// 手机号
	private String phone;

    @Override
	public Specification createSpec() {
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		// 是否去除管理员用户的标志(信号量)
		boolean mark = false;
		// 如果当前登录用户是管理员，页面上就去除"管理员"和"超级管理员"用户，防止管理员修改其他管理员信息
		if (current.getMember().getMemberName().equals("管理员")){
			mark = true;
		}
		Specification<Employee> specification = Specifications.<Employee>and()
				//如果还有其它字段,就再加上
				.like(StringUtils.isNoneBlank(username), "username", "%" + username + "%")
				.eq(StringUtils.isNoneBlank(phone),"phone",phone)
				// 页面上去除"管理员"和"超级管理员"用户
				.notIn(mark,"member",4L,5L)
				.build();
		//返回查询规则
		return specification;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "EmployeeQuery{" +
				"username='" + username + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}