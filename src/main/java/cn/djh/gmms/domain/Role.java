package cn.djh.gmms.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色类（为了控制权限）
 */
@Entity
@Table(name = "role")
public class Role extends BaseDomain {
    private String roleName; //角色名称
    private String sn; //角色编码

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions = new ArrayList<>();

    public Role() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                ", sn='" + sn + '\'' +
                ", id=" + id +
                '}';
    }
}
