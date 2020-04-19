package cn.djh.gmms.service.impl;

import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.query.EmployeeQuery;
import cn.djh.gmms.repository.EmployeeRepository;
import cn.djh.gmms.service.IEmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (Employee)表服务实现类
 *
 * @author masterDJ
 * @since 2020-01-19 11:03:02
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Long> implements IEmployeeService {

    @Autowired
	private EmployeeRepository employeeRepository;

    @Override
    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public Integer findSameUserNum(String username) {
        return employeeRepository.findSameUserNum(username);
    }

    /**
     * 统计用户总数
     * @return
     */
    @Override
    public Long countMeneger() {
        return employeeRepository.countMeneger();
    }

    /**
     * 条件查询 会员用户积分分页数据
     * @param query
     * @return
     */
    @Override
    public List<Employee> queryScorePage(EmployeeQuery query) {
        // 根据查询条件的空值情况，使用查询的统计方法
        if(StringUtils.isNoneBlank(query.getUsername())&&StringUtils.isNoneBlank(query.getPhone())){
            return employeeRepository.queryScorePageByNameAndPhone("%"+query.getUsername()+"%",query.getPhone(),query.getJpaPage()*query.getPageSize(),query.getPageSize());
        }else if (StringUtils.isNoneBlank(query.getUsername())&&!StringUtils.isNoneBlank(query.getPhone())){
            return employeeRepository.queryScorePageByName("%"+query.getUsername()+"%",query.getJpaPage()*query.getPageSize(),query.getPageSize());
        }else if (!StringUtils.isNoneBlank(query.getUsername())&&StringUtils.isNoneBlank(query.getPhone())){
            return employeeRepository.queryScorePageByPhone(query.getPhone(),query.getJpaPage()*query.getPageSize(),query.getPageSize());
        }else {
            return employeeRepository.queryScorePage(query.getJpaPage()*query.getPageSize(),query.getPageSize());
        }
    }

    /**
     * 统计会员用户积分记录总数
     * @return
     */
    @Override
    public Long countScorePage(EmployeeQuery query) {
        // 根据查询条件的空值情况，使用对应的统计方法
        if(StringUtils.isNoneBlank(query.getUsername())&&StringUtils.isNoneBlank(query.getPhone())){
            return employeeRepository.countScorePageByNameAndPhone("%"+query.getUsername()+"%",query.getPhone());
        }else if (StringUtils.isNoneBlank(query.getUsername())&&!StringUtils.isNoneBlank(query.getPhone())){
            return employeeRepository.countScorePageByName("%"+query.getUsername()+"%");
        }else if (!StringUtils.isNoneBlank(query.getUsername())&&StringUtils.isNoneBlank(query.getPhone())){
            return employeeRepository.countScorePageByPhone(query.getPhone());
        }else {
            return employeeRepository.countScorePage();
        }
    }

    /**
     * 重置会员用户的会员类型为普通会员
     */
    @Transactional
    @Override
    public void resetMember() {
        employeeRepository.resetMember();
    }

}