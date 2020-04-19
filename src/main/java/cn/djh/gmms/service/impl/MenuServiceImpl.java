package cn.djh.gmms.service.impl;

import cn.djh.gmms.domain.Menu;
import cn.djh.gmms.repository.MenuRepository;
import cn.djh.gmms.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (Menu)表服务实现类
 *
 * @author masterDJ
 * @since 2020-01-20 10:55:30
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements IMenuService {

    @Autowired
	private MenuRepository menuRepository;

    // 查询所有父菜单项
    @Override
    public List<Menu> findParenrtMenus() {
        return menuRepository.findParenrtMenus();
    }

    // 查询所有子菜单项
    @Override
    public List<Menu> findAllChildrenMenus() {
        return menuRepository.findAllChildrenMenus();
    }

    // 根据用户ID查询所有子菜单项
    @Override
    public List<Menu> findChildrenMenus(Long id) {
        // 创建父菜单容器
        List<Menu> parentMenus = new ArrayList<>();
        // 拿到子菜单
        List<Menu> childrenMenus = menuRepository.findChildrenMenus(id);

        for (Menu chilrenMenu: childrenMenus ) {
            //拿到子菜单对应的父菜单
            Menu parent = chilrenMenu.getParent();
            if (parentMenus.contains(parent)){// 如果父菜单容器有该子菜单的父菜单,就把这个父菜单加入父菜单容器
                int i = parentMenus.indexOf(parent);
                Menu parentMenu = parentMenus.get(i);// 拿到父菜单容器对应子菜单的父菜单
                parentMenu.getChildren().add(chilrenMenu);// 把这个子菜单加入父菜单
            } else {//如果没有，再单独把父菜单放进去
                if(parent==null){// 如果子菜单的父菜单为空，就把当前子菜单作为新父菜单加入父菜单容器
                    parentMenus.add(chilrenMenu);
                }else {
                    parentMenus.add(parent); // 向父菜单容器就加入新的父菜单
                    List<Menu> childrenList = parent.getChildren();
                    childrenList.add(chilrenMenu); // 向该新父菜单加入子菜单
                }
            }
        }
        return parentMenus;
    }

}