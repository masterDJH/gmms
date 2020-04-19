package cn.djh.gmms.repository;

import cn.djh.gmms.domain.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * (Employee)表数据库访问层
 *
 * @author masterDJ
 * @since 2020-01-19 11:03:02
 */
public interface EmployeeRepository extends BaseRepository<Employee, Long> {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    Employee findByUsername(String username);

    /**
     * 通过邮箱名查询用户
     * @param email
     * @return
     */
    Employee findByEmail(String email);

    /**
     * 查询同名用户数量
     * @param username
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT COUNT(id) FROM employee WHERE username = ?1")
    Integer findSameUserNum(String username);

    /**
     * 统计管理员用户总数
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT COUNT(id) FROM employee WHERE member_id = 4 OR member_id = 5")
    Long countMeneger();

    /**
     * 查询会员用户积分分页数据
     */
    @Query(nativeQuery = true,value = "SELECT * FROM employee e LEFT JOIN member m ON e.member_id = m.id WHERE m.memberName NOT LIKE '%管理员%' ORDER by e.id limit ?1, ?2")
    List<Employee> queryScorePage(Integer jpaPage, Integer pageSize);

    /**
     * 用户名 查询会员用户积分分页数据
     */
    @Query(nativeQuery = true,value = "SELECT * FROM employee e LEFT JOIN member m ON e.member_id = m.id WHERE m.memberName NOT LIKE '%管理员%' AND e.username LIKE ?1 ORDER by e.id limit ?2, ?3")
    List<Employee> queryScorePageByName(String username,Integer jpaPage, Integer pageSize);

    /**
     * 电话 查询会员用户积分分页数据
     */
    @Query(nativeQuery = true,value = "SELECT * FROM employee e LEFT JOIN member m ON e.member_id = m.id WHERE m.memberName NOT LIKE '%管理员%' AND e.phone = ?1 ORDER by e.id limit ?2, ?3")
    List<Employee> queryScorePageByPhone(String phone,Integer jpaPage, Integer pageSize);

    /**
     * 用户名、电话 查询会员用户积分分页数据
     */
    @Query(nativeQuery = true,value = "SELECT * FROM employee e LEFT JOIN member m ON e.member_id = m.id WHERE m.memberName NOT LIKE '%管理员%' AND e.username LIKE ?1 AND e.phone = ?2 ORDER by e.id limit ?3, ?4")
    List<Employee> queryScorePageByNameAndPhone(String username,String phone,Integer jpaPage, Integer pageSize);

    /**
     * 统计会员用户积分记录总数
     */
    @Query(nativeQuery = true,value = "SELECT COUNT(e.id) FROM employee e LEFT JOIN member m ON e.member_id = m.id WHERE m.memberName NOT LIKE '%管理员%'")
    Long countScorePage();
    /**
     * 用户名 统计会员用户积分记录总数
     */
    @Query(nativeQuery = true,value = "SELECT COUNT(e.id) FROM employee e LEFT JOIN member m ON e.member_id = m.id WHERE m.memberName NOT LIKE '%管理员%' AND e.username LIKE ?1")
    Long countScorePageByName(String username);
    /**
     * 电话 统计会员用户积分记录总数
     */
    @Query(nativeQuery = true,value = "SELECT COUNT(e.id) FROM employee e LEFT JOIN member m ON e.member_id = m.id WHERE m.memberName NOT LIKE '%管理员%' AND e.phone = ?1")
    Long countScorePageByPhone(String phone);
    /**
     * 用户名、电话 统计会员用户积分记录总数
     */
    @Query(nativeQuery = true,value = "SELECT COUNT(e.id) FROM employee e LEFT JOIN member m ON e.member_id = m.id WHERE m.memberName NOT LIKE '%管理员%' AND e.username LIKE ?1 AND e.phone = ?2")
    Long countScorePageByNameAndPhone(String username,String phone);

    /**
     * 重置会员用户的会员类型为普通会员
     */
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE employee LEFT JOIN member ON employee.member_id = member.id SET employee.member_id = (SELECT id FROM member WHERE memberName = '普通会员') WHERE member.memberName LIKE '%会员%'")
    void resetMember();
}