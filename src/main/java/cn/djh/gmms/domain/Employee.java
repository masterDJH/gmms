package cn.djh.gmms.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee extends BaseDomain {

    @Excel(name = "用户名")
    @NotNull(message = "用户名不能为空")
    private String username;
    private String password;
    @Excel(name = "电话")
    private String phone;
    @Excel(name = "邮箱")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @Excel(name = "年龄")
    @Max(value = 100,message = "最大年龄不超过100岁")
    @Min(value = 0,message = "最小年龄不低于0岁")
    private Integer age;

    private String headImg;// 头像路径
    @Excel(name = "积分")
    @Min(value = 0,message = "最低会员积分不低于0分")
    private BigDecimal score;// 会员积分

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")//设置外键名（列名）, 会员等级id
    @ExcelEntity
    private Member member;// 会员等级

    @ManyToMany
    @JoinTable(name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Transient// 临时属性,和数据库没有关系,JPA不管理这个属性
    @JsonIgnore
    private MultipartFile headImage;		//复杂表单（存头像图片）

    public Employee() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public MultipartFile getHeadImage() {
        return headImage;
    }

    public void setHeadImage(MultipartFile headImage) {
        this.headImage = headImage;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", headImg='" + headImg + '\'' +
                ", score=" + score +
                ", id=" + id +
                "}\n";
    }
}
