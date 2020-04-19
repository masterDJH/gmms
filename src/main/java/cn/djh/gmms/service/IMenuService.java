package cn.djh.gmms.service;

import cn.djh.gmms.domain.Menu;

import java.util.List;

/**
 * (Menu)表服务接口
 *
 * @author masterDJ
 * @since 2020-01-20 10:55:30
 */
public interface IMenuService extends IBaseService<Menu,Long> {

    // 查询所有父菜单项
    List<Menu> findParenrtMenus();

    // 查询所有子菜单项
    List<Menu> findAllChildrenMenus();

    // 根据用户ID查询所有子菜单项
    List<Menu> findChildrenMenus(Long id);

}