package cn.djh.gmms.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分消费明细(不含会员等级充值)
 */
@Entity
@Table(name="scoredetails")
public class ScoreDetails extends BaseDomain {
    private BigDecimal score;// 消费积分数
    private Date scDate;// 积分消费时间

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")//设置外键名（列名）, 消费者id（会员）
    private Employee employee;// 消费者

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")//设置外键名（列名）, 消费课程id
    private Course course;// 消费课程

    public ScoreDetails() {
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Date getScDate() {
        return scDate;
    }

    public void setScDate(Date scDate) {
        this.scDate = scDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "ScoreDetails{" +
                "score=" + score +
                ", scDate=" + scDate +
                ", id=" + id +
                '}';
    }
}
