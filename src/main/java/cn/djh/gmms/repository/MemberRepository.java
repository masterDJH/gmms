package cn.djh.gmms.repository;

import cn.djh.gmms.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * (Member)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-01-21 00:30:38
 */
public interface MemberRepository extends BaseRepository<Member, Long> {

    /**
     * 查询同等级会员等级
     * @param memberGrade
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT COUNT(id) FROM member WHERE memberGrade = ?1")
    Integer findSameGradeNum(Integer memberGrade);

    /**
     * 查询同名会员等级总数
     * @param memberName
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT COUNT(id) FROM member WHERE memberName = ?1")
    Integer findSameMemberNameNum(String memberName);

    /**
     * 查询指定名字的会员类型
     * @param roleName
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM member WHERE memberName = ?1")
    Member findByName(String roleName);

    /**
     * 按会员等级grade排序查询
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM member ORDER BY memberGrade ASC")
    List<Member> findOrderByMemberGrade();
}