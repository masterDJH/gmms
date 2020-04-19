package cn.djh.gmms.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员充值明细（仅含充值记录）
 */
@Entity
@Table(name="paydetails")
public class PayDetails extends BaseDomain {
    private BigDecimal cost;// 充值金额
    private Date payDate;// 充值时间

    // 【会员等级为空，表示是仅积分充值；会员等级不为空，表示是会员等级充值】
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")//设置外键名（列名）, 会员等级id
    private Member member;// 会员等级

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")//设置外键名（列名）, 充值者id（会员）
    private Employee employee;// 充值者

    public PayDetails() {
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "PayDetails{" +
                "cost=" + cost +
                ", payDate=" + payDate +
                ", id=" + id +
                '}';
    }
}
