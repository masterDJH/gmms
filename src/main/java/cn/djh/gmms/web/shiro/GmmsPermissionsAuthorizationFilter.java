package cn.djh.gmms.web.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GmmsPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

	/**
	 * 当请求是AJAX请求时,shiro会通过AJAX请求的返回的参数result将页面以代码形式返回回来。
	 * 导致result没有mes属性，影响删除数据的操作结果提示信息（undefined）
	 * 所以要重写权限过滤器，当用户执行无权限的删除时，直接返回 {"success":false,"mes":"没有权限"} 操作结果信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

		Subject subject = this.getSubject(request, response);
		if (subject.getPrincipal()==null){
			// 没有登录成功后的操作
			this.saveRequestAndRedirectToLogin(request, response);
		} else {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			// 根据请求头Header确定是什么请求
			String xRequestedWith = httpRequest.getHeader("X-Requested-With");
//			System.out.println("拒绝权限："+httpRequest.getRequestURL());

			if ("XMLHttpRequest".equals(xRequestedWith)){// 是AJAX请求,返回无权限的提示信息
				httpResponse.setContentType("text/json; charset=UTF-8");
				httpResponse.getWriter().print("{\"success\":false,\"mes\":\"没有权限\"}");
			} else {// 不是AJAX请求,按无权限的正常流程执行
				String unauthorizedUrl = this.getUnauthorizedUrl();
				if (StringUtils.hasText(unauthorizedUrl)) {//如果有未授权页面跳转过去
					WebUtils.issueRedirect(request, response, unauthorizedUrl);
				} else {//否则返回401未授权状态码
					WebUtils.toHttp(response).sendError(401);
				}
			}
		}
		return false;
	}
}
