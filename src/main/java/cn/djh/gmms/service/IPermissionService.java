package cn.djh.gmms.service;

import cn.djh.gmms.domain.Permission;

import java.util.Set;

/**
 * (Permission)表服务接口
 *
 * @author masterDJ
 * @since 2020-01-21 16:40:54
 */
public interface IPermissionService extends IBaseService<Permission,Long> {

    Set<String> findSnByLoginUser();
}