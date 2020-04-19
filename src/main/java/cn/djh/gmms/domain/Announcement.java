package cn.djh.gmms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 公告信息
 */
@Entity
@Table(name="announcement")
public class Announcement extends BaseDomain {
    private String title;// 公告标题
    private String info;// 公告信息
    private Date anDate = new Date();// 发布时间（系统生成）

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")//设置外键名（列名）, 发布人员id（超级管理员、管理员）
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private Employee employee;// 发布人员

    public Announcement() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")// 获取时间时,转换时间格式
    public Date getAnDate() {
        return anDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")// 设置时间时,转换时间格式
    public void setAnDate(Date anDate) {
        this.anDate = anDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", anDate=" + anDate +
                ", employee=" + employee +
                ", id=" + id +
                '}';
    }
}
