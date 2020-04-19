package cn.djh.gmms.service.impl;

import cn.djh.gmms.domain.Member;
import cn.djh.gmms.repository.MemberRepository;
import cn.djh.gmms.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Member)表服务实现类
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:38
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements IMemberService {

    @Autowired
	private MemberRepository memberRepository;

    /**
     * 查询同等级会员等级
     * @param memberGrade
     * @return
     */
    @Override
    public Integer findSameGradeNum(Integer memberGrade) {
        return memberRepository.findSameGradeNum(memberGrade);
    }

    /**
     * 查询同名会员等级
     * @param memberName
     * @return
     */
    @Override
    public Integer findSameMemberNameNum(String memberName) {
        return memberRepository.findSameMemberNameNum(memberName);
    }

    /**
     * 查询指定名字的会员类型
     * @param roleName
     * @return
     */
    @Override
    public Member findByName(String roleName) {
        return memberRepository.findByName(roleName);
    }

    /**
     * 按会员等级grade排序查询
     * @return
     */
    @Override
    public List<Member> findOrderByMemberGrade() {
        return memberRepository.findOrderByMemberGrade();
    }
}