package cn.djh.gmms.service;

import cn.djh.gmms.domain.Role;

/**
 * (Role)表服务接口
 *
 * @author masterDJ
 * @since 2020-01-21 11:33:03
 */
public interface IRoleService extends IBaseService<Role,Long> {

    Role findBySn(String admin);

    Role findByName(String roleName);
}