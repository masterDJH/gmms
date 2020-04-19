package cn.djh.gmms.repository;

import cn.djh.gmms.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * (Role)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-01-21 11:33:03
 */
public interface RoleRepository extends BaseRepository<Role, Long> {

    /**
     * 根据角色的sn编码查询
     * @param admin
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM role WHERE sn = ?1")
    Role findBySn(String admin);

    // 根据角色的名字查询角色
    @Query("select r from Role r where r.roleName = ?1")
    Role findByName(String roleName);

}