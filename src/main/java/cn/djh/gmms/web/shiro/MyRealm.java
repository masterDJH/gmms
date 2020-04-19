package cn.djh.gmms.web.shiro;

import cn.djh.gmms.Exception.RepetLoginException;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.service.IPermissionService;
import cn.djh.gmms.utils.MD5Util;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// 自定义 Realm
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IPermissionService permissionService;
	@Autowired
	private SessionDAO sessionDAO;

	/**
	 *  实现授权功能
	 *
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		//从数据库中获取当前用户拥有的权限,并放且放到授权对象authorizationInfo中
		Set<String> perms = permissionService.findSnByLoginUser();
		authorizationInfo.setStringPermissions(perms);
		// 返回授权对象authorizationInfo
		return authorizationInfo;
	}

	/**
	 *	实现身份认证功能
	 *	如果返回值为 null,表示用户不存在,shiro会抛出 UnknownAccountException
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		boolean loginMark = true;// 允许登录标志，默认可登录
		// 获取令牌
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 获取用户名
		String username = token.getUsername();
        // 从数据库中拿取密码
        Employee loginUser = employeeService.findByUsername(username);

		// 单点登录控制
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for (Session session: sessions ) {
            String loginUsername = String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));;
//            System.out.println("已登录："+loginUsername);
            if(loginUser.toString().equals(loginUsername)){// 当前用户已经登录
                loginMark =false;// 标记置为false，即该用户已经登录，则不允许其他人登录该账户
				break;//退出遍历，已找到相同用户，不必继续遍历
            }
		}
//		System.out.println("允许登录："+loginMark);
		if(loginMark==true){// 该用户以前未登录，现在允许登录（单点登录）
            // 判断用户存在与否
			if (loginUser==null){// 不存在该用户，则返回null
				return null;
			}
			/**
			 * 创建一个简单的身份信息
			 *	Object principal:主体(可以随便写)		登录成功后，想要保存的对象
			 * 	Object credentials：凭证(就是密码)		用户名对应的对象在数据库中的密码
			 * 	String realmName : realm的名称(可以随便写)
			 *
			 * 	注意：shiro会通过返回的 authenticationInfo对象自动判断密码是否正确
			 */
			// 拿到盐值对象(ByteSource),用于shiro的自动密码匹配
			ByteSource salt = ByteSource.Util.bytes(MD5Util.SALT);
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(loginUser, loginUser.getPassword(),salt, "myRealm");

			return authenticationInfo;
		}else {
			// 抛出重复登录异常
			throw new RepetLoginException();
		}
	}

}
