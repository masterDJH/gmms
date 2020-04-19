package cn.djh.gmms.service.impl;

import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.Permission;
import cn.djh.gmms.repository.PermissionRepository;
import cn.djh.gmms.service.IPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * (Permission)表服务实现类
 *
 * @author masterDJ
 * @since 2020-01-21 16:40:54
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long> implements IPermissionService {

    @Autowired
	private PermissionRepository permissionRepository;

    /**
     * 获取当前用户拥有的权限
     * @return
     */
    @Override
    public Set<String> findSnByLoginUser() {
        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        // 获取当前登录的用户
        Employee employee = (Employee) subject.getPrincipal();
        return permissionRepository.findSnByLoginUser(employee.getId());
    }
}