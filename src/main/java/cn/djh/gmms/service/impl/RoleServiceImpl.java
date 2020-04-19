package cn.djh.gmms.service.impl;

import cn.djh.gmms.domain.Role;
import cn.djh.gmms.repository.RoleRepository;
import cn.djh.gmms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Role)表服务实现类
 *
 * @author masterDJ
 * @since 2020-01-21 11:33:03
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements IRoleService {

    @Autowired
	private RoleRepository roleRepository;

    /**
     * 根据角色的sn编码查询角色
     * @param admin
     * @return
     */
    @Override
    public Role findBySn(String admin) {
        return roleRepository.findBySn(admin);
    }

    /**
     * 根据角色的名字查询角色
     * @param roleName
     * @return
     */
    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}