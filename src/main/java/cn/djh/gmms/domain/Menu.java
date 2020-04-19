package cn.djh.gmms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 因为jsonplugin用的是Java的内审机制.hibernate会给被管理的pojo加入一个hibernateLazyInitializer属性,
 * jsonplugin会把hibernateLazyInitializer也拿出来操作,并读取里面一个不能被反射操作的属性就产生了这个异常
 * 所以将hibernateLazyInitializer进行忽略
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@Entity
@Table(name="menu")
public class Menu extends BaseDomain {
    private String name;// 菜单名称
    private String url;// 菜单路径（与权限路径匹配）
    private String icon;// 菜单图标

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")// 指定列名
    @JsonIgnore
    private Menu parent;// 父菜单

    @Transient// 临时属性,和数据库没有关系,JPA不管理这个属性
    private List<Menu> children = new ArrayList<>();

    public Menu() {
    }

    public String getName() {
        return name;
    }
    public String getText() { // 以匹配前提页面对菜单树的格式要求
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }
    public String getIconCls() { // 以匹配前提页面对菜单树的格式要求
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                '}';
    }
}
