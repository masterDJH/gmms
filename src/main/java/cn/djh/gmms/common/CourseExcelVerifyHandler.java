package cn.djh.gmms.common;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.djh.gmms.domain.Course;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.service.ICourseService;
import cn.djh.gmms.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 导入数据的自定义验证类(验证课程是否已存在)
 */
@Component
public class CourseExcelVerifyHandler implements IExcelVerifyHandler<Course> {

	@Autowired
	private ICourseService courseService;

	/**
	 * 自定义验证 验证课程是否已存在
	 * @param course
	 * @return
	 */
	@Override
	public ExcelVerifyHandlerResult verifyHandler(Course course) {
		ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult(true);
		if (courseService.findByCourseName(course.getCourseName())!=null) {// 同名课程不为null ,表示已存在同名课程
			result.setSuccess(false);
			result.setMsg("课程已存在");
		}
		return result;
	}
}
