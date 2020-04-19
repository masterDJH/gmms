package cn.djh.gmms.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name="course")
public class Course extends BaseDomain {
    @Excel(name = "课程名")
    @NotNull(message = "课程名不能为空")
    private String courseName;// 课程名称
    @Excel(name = "课程描述")
    private String descs;// 课程描述
    private String pic;// 课程图片

    @Excel(name = "课程原价")
    @Min(value = 0,message = "最低课程原价不低于0积分")
    @NotNull(message = "课程原价不能为空")
    private BigDecimal courseCost;// 课程原价费用（积分）

    @Excel(name = "课程时长")
    @Min(value = 0,message = "课程时长必须大于0")
    @NotNull(message = "课程时长不能为空")
    private Integer courseTime;// 课程时长（月数）

    @Transient// 临时属性,和数据库没有关系,JPA不管理这个属性
    @JsonIgnore
    private MultipartFile courseImg;		//复杂表单（存课程图片）

    public Course() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public BigDecimal getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(BigDecimal courseCost) {
        this.courseCost = courseCost;
    }

    public Integer getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(Integer courseTime) {
        this.courseTime = courseTime;
    }

    public MultipartFile getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(MultipartFile courseImg) {
        this.courseImg = courseImg;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", descs='" + descs + '\'' +
                ", pic='" + pic + '\'' +
                ", courseCost=" + courseCost +
                ", courseTime=" + courseTime +
                ", id=" + id +
                '}';
    }
}
