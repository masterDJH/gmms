package cn.djh.gmms.repository;

import cn.djh.gmms.domain.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * (Menu)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-01-20 10:55:30
 */
public interface MenuRepository extends BaseRepository<Menu, Long> {

    // 查询所有父菜单项
    @Query("select o from Menu o where o.url is null")
    List<Menu> findParenrtMenus();

    // 查询所有子菜单项
    @Query("select distinct m from Employee e join e.roles r join r.permissions p join p.menu m")
    List<Menu> findAllChildrenMenus();

    // 根据用户ID查询所有子菜单项
    @Query("select distinct m from Employee e join e.roles r join r.permissions p join p.menu m where e.id=?1")
    List<Menu> findChildrenMenus(Long employeeId);
}