package cn.djh.gmms.domain;


import javax.persistence.*;

@Entity
@Table(name="permission")
public class Permission extends BaseDomain {
    private String perName;// 权限名称
    private String perUrl;// 权限路径
    private String descs;// 权限详情描述
    private String sn;// 权限编码（权限的控制编码）

    @ManyToOne
    @JoinColumn(name = "menu_id")//设置外键名（列名）, 菜单id
    private Menu menu;//菜单

    public Permission() {
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerUrl() {
        return perUrl;
    }

    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "perName='" + perName + '\'' +
                ", perUrl='" + perUrl + '\'' +
                ", descs='" + descs + '\'' +
                ", sn='" + sn + '\'' +
                ", id=" + id +
                '}';
    }
}
