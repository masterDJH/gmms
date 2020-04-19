package cn.djh.gmms.domain.vo;

import cn.djh.gmms.domain.Course;

import java.math.BigDecimal;
import java.util.List;

public class CourseVO {
    private Long id;
    private String courseName;// 课程名称
    private String descs;// 课程描述
    private String pic;// 课程图片
    private Integer courseCost;// 课程原价费用（积分）
    private Integer courseTime;// 课程所耗时间（月数）

    public CourseVO(Course course) {
        this.id = course.getId();
        this.courseName = course.getCourseName();
        this.descs = course.getDescs();
        this.pic = course.getPic();
        this.courseCost = course.getCourseCost().intValue();
        this.courseTime = course.getCourseTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(Integer courseCost) {
        this.courseCost = courseCost;
    }

    public Integer getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(Integer courseTime) {
        this.courseTime = courseTime;
    }
}
