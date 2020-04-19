package cn.djh.gmms.web.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.djh.gmms.common.CourseExcelVerifyHandler;
import cn.djh.gmms.common.EmployeeExcelVerifyHandler;
import cn.djh.gmms.common.RoleConstant;
import cn.djh.gmms.domain.Course;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.Member;
import cn.djh.gmms.domain.Role;
import cn.djh.gmms.query.CourseQuery;
import cn.djh.gmms.query.EmployeeQuery;
import cn.djh.gmms.service.ICourseService;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.service.IMemberService;
import cn.djh.gmms.service.IRoleService;
import cn.djh.gmms.utils.MD5Util;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出数据
 */
@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private ICourseService courseService;

	/**
	 * 跳转至导入功能的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "export/index";

	}

	/**
	 * 导出用户表格数据
	 * @param query
	 * @param map
	 * @return
	 */
	@RequestMapping("/exportEmpXlsx")
	public String exportEmpXlsx(EmployeeQuery query, ModelMap map){
		// 获取数据集
		List<Employee> list = employeeService.queryAll(query);

		// 设置属性
		ExportParams params = new ExportParams("用户表", "用户表", ExcelType.XSSF);
		// params.setFreezeCol(1);// 冻结列的数量
		map.put(NormalExcelConstants.DATA_LIST, list);        // 设置数据集合
		map.put(NormalExcelConstants.CLASS, Employee.class);// 设置导出实体
		map.put(NormalExcelConstants.PARAMS, params);        // 设置参数
		map.put(NormalExcelConstants.FILE_NAME, "employee");// 设置文件名称

		/* 根据返回的名称去找一个bean对象(在SpringMVC称之为view)
		   返回View名称 "easypoiExcelView"
		*/
		return NormalExcelConstants.EASYPOI_EXCEL_VIEW;
	}

    /**
     * 导出课程表格数据
     * @param query
     * @param map
     * @return
     */
	@RequestMapping("/exportCouXlsx")
	public String exportCouXlsx(CourseQuery query, ModelMap map){
        // 获取数据集
        List<Course> list = courseService.queryAll(query);

        // 设置属性
        ExportParams params = new ExportParams("课程表", "课程表", ExcelType.XSSF);
        // params.setFreezeCol(1);// 冻结列的数量
        map.put(NormalExcelConstants.DATA_LIST, list);        // 设置数据集合
        map.put(NormalExcelConstants.CLASS, Course.class);// 设置导出实体
        map.put(NormalExcelConstants.PARAMS, params);        // 设置参数
        map.put(NormalExcelConstants.FILE_NAME, "course");// 设置文件名称

		/* 根据返回的名称去找一个bean对象(在SpringMVC称之为view)
		   返回View名称 "easypoiExcelView"
		*/
        return NormalExcelConstants.EASYPOI_EXCEL_VIEW;
	}
}
