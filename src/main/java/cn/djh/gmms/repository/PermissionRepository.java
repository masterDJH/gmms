package cn.djh.gmms.repository;

import cn.djh.gmms.domain.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * (Permission)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-01-21 16:40:54
 */
public interface PermissionRepository extends BaseRepository<Permission, Long> {

    /**
     * 获取当前用户拥有的权限
     * @param id
     * @return
     */
    @Query("select distinct p.sn from Employee e join e.roles r join r.permissions p where e.id =?1")
    Set<String> findSnByLoginUser(Long id);
}