package cn.djh.gmms.domain.vo;

import java.util.Date;

public class OrderCourseVO {
    private Long id;
    private String courseName;// 课程名称
    private String descs;// 课程描述
    private String pic;// 课程图片
    private Integer courseCost;// 课程原价费用（积分）
    private Integer courseTime;// 课程所耗时间（月数）
    private Date scDate;// 订购时间

    public OrderCourseVO(){
    }

    public OrderCourseVO(Long id, String courseName, String descs, String pic, Integer courseCost, Integer courseTime, Date scDate) {
        this.id = id;
        this.courseName = courseName;
        this.descs = descs;
        this.pic = pic;
        this.courseCost = courseCost;
        this.courseTime = courseTime;
        this.scDate = scDate;
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

    public Date getScDate() {
        return scDate;
    }

    public void setScDate(Date scDate) {
        this.scDate = scDate;
    }

    @Override
    public String toString() {
        return "OrderCourseVO{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", descs='" + descs + '\'' +
                ", pic='" + pic + '\'' +
                ", courseCost=" + courseCost +
                ", courseTime=" + courseTime +
                ", scDate=" + scDate +
                '}';
    }
}
