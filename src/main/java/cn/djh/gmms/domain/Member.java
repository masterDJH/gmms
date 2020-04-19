package cn.djh.gmms.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@Entity
@Table(name = "member")
public class Member extends BaseDomain {
    private Integer memberGrade;// 会员等级（普通会员：1, 黄金会员：2, 钻石会员：3）
    @Excel(name = "会员等级")
    private String memberName;// 会员等级名字（普通会员, 黄金会员, 钻石会员）
    private Double discount;// 折扣数（0.5 ~ 0.9折）
    private BigDecimal memberCost;// 升级到当前会员等级所需费用（普通->钻石：100积分）

    public Member() {
    }

    public Integer getMemberGrade() {
        return memberGrade;
    }

    public void setMemberGrade(Integer memberGrade) {
        this.memberGrade = memberGrade;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public BigDecimal getMemberCost() {
        return memberCost;
    }

    public void setMemberCost(BigDecimal memberCost) {
        this.memberCost = memberCost;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberGrade=" + memberGrade +
                ", memberName='" + memberName + '\'' +
                ", discount=" + discount +
                ", memberCost=" + memberCost +
                ", id=" + id +
                '}';
    }
}
