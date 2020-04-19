package cn.djh.gmms.service;

import cn.djh.gmms.domain.Member;

import java.util.List;

/**
 * (Member)表服务接口
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:38
 */
public interface IMemberService extends IBaseService<Member,Long> {

    Integer findSameGradeNum(Integer memberGrade);

    Integer findSameMemberNameNum(String memberName);

    Member findByName(String roleName);

    List<Member> findOrderByMemberGrade();
}