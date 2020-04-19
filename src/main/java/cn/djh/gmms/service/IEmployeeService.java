package cn.djh.gmms.service;

import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.query.EmployeeQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * (Employee)表服务接口
 *
 * @author masterDJ
 * @since 2020-01-19 11:03:02
 */
public interface IEmployeeService extends IBaseService<Employee,Long> {

    Employee findByUsername(String username);
    Employee findByEmail(String email);

    Integer findSameUserNum(String username);

    Long countMeneger();

    List<Employee> queryScorePage(EmployeeQuery query);

    Long countScorePage(EmployeeQuery query);

    void resetMember();
}