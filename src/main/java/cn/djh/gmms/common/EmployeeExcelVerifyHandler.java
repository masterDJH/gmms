package cn.djh.gmms.common;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 导入数据的自定义验证类(验证用户是否唯一)
 */
@Component
public class EmployeeExcelVerifyHandler implements IExcelVerifyHandler<Employee> {

	@Autowired
	private IEmployeeService employeeService;

	/**
	 * 自定义验证 验证用户是否唯一
	 * @param employee
	 * @return
	 */
	@Override
	public ExcelVerifyHandlerResult verifyHandler(Employee employee) {
		ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult(true);
		if (employeeService.findSameUserNum(employee.getUsername()) > 0) {// 同名用户数量大于 0 ,表示存在同名用户
			result.setSuccess(false);
			result.setMsg("用户名重复");
		}
		return result;
	}
}
