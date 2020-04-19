package cn.djh.gmms.web.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.djh.gmms.common.CourseExcelVerifyHandler;
import cn.djh.gmms.common.EmployeeExcelVerifyHandler;
import cn.djh.gmms.common.RoleConstant;
import cn.djh.gmms.domain.Course;
import cn.djh.gmms.domain.Employee;
import cn.djh.gmms.domain.Member;
import cn.djh.gmms.domain.Role;
import cn.djh.gmms.service.ICourseService;
import cn.djh.gmms.service.IEmployeeService;
import cn.djh.gmms.service.IMemberService;
import cn.djh.gmms.service.IRoleService;
import cn.djh.gmms.utils.MD5Util;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入数据
 */
@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private EmployeeExcelVerifyHandler employeeExcelVerifyHandler;
	@Autowired
	private ICourseService courseService;
	@Autowired
	private CourseExcelVerifyHandler courseExcelVerifyHandler;

	/**
	 * 跳转至导入功能的index页面
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "import/index";

	}

	/**
	 * 导入用户表格数据(带数据格式导入验证)
	 * @param empFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/employeeXlsx")
	public String employeeXlsx(MultipartFile empFile, HttpServletResponse response, Model model){
		ImportParams params = new ImportParams();
		params.setHeadRows(1);// 设置Head为1行,这样读取数据时就跳过这行Head
		params.setNeedVerfiy(true);// 开启数据导入验证
		params.setVerifyHandler(employeeExcelVerifyHandler);// 在原有的数据验证规则基础上,再加上自定义的数据验证类对象

		// 导入数据
		ExcelImportResult<Employee> result = null;
		if (empFile.isEmpty()){// 导入文件为空，直接返回导入界面
			return "import/index";
		}
		try {
			result = ExcelImportUtil.importExcelMore(empFile.getInputStream(),
                    Employee.class, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 对通过验证的正确数据进行保存
		result.getList().forEach(e -> {
			// 根据会员类型名称拿到它的会员类型对象,再放到对应的用户对象中
			Member member  = memberService.findByName(e.getMember().getMemberName());
			e.setMember(member);
			// 默认新用户角色为“会员”,再放到对应的用户对象中
			Role role = roleService.findByName(RoleConstant.DefaultImportUserRole);
			List roleList = new ArrayList<Role>();
			roleList.add(role);
			e.setRoles(roleList);
			// 设置一个默认密码("master")
			e.setPassword(MD5Util.createMD5Str(RoleConstant.DefaultImportUserPassword));
			// 保存数据
			employeeService.save(e);
			model.addAttribute("empResult","新用户导入成功");
		});
		//如果有错误数据，就直接导出错误数据文件到前台
		OutputStream ouputStream = null;
		try {
			if (result.isVerfiyFail()){
				Workbook failWorkbook = result.getFailWorkbook();
				//把这个错误数据文件导出
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //mime类型
				response.setHeader("Content-disposition", "attachment;filename=error_employee.xlsx");
				response.setHeader("Pragma", "No-cache");//设置不要缓存
				ouputStream = response.getOutputStream();
				// 写出数据
				failWorkbook.write(ouputStream);
				ouputStream.flush();
				model.addAttribute("empResult","新用户导入失败,请查看导入失败的用户");
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {// 关闭流
			try {
                if(ouputStream!=null){
                    ouputStream.close();
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "import/index";
	}

	/**
	 * 导入用户表格数据(带数据格式导入验证)
	 * @param courseFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/courseXlsx")
	public String courseXlsx(MultipartFile courseFile,HttpServletResponse response, Model model){
		ImportParams params = new ImportParams();
		params.setHeadRows(1);// 设置Head为1行,这样读取数据时就跳过这行Head
		params.setNeedVerfiy(true);// 开启数据导入验证
		params.setVerifyHandler(courseExcelVerifyHandler);// 在原有的数据验证规则基础上,再加上自定义的数据验证类对象

		// 导入数据
		ExcelImportResult<Course> result = null;
		if (courseFile.isEmpty()){// 导入文件为空，直接返回导入界面
			return "import/index";
		}
		try {
			result = ExcelImportUtil.importExcelMore(courseFile.getInputStream(),
                    Course.class, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 对通过验证的正确数据进行保存
		result.getList().forEach(e -> {
			// 保存数据
			courseService.save(e);
			model.addAttribute("couResult","新课程导入成功");
		});
		//如果有错误数据，就直接导出错误数据文件到前台
		OutputStream ouputStream = null;
		try {
			if (result.isVerfiyFail()){
				Workbook failWorkbook = result.getFailWorkbook();
				//把这个错误数据文件导出
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //mime类型
				response.setHeader("Content-disposition", "attachment;filename=error_course.xlsx");
				response.setHeader("Pragma", "No-cache");//设置不要缓存
				ouputStream = response.getOutputStream();
				// 写出数据
				failWorkbook.write(ouputStream);
				ouputStream.flush();
				model.addAttribute("couResult","新课程导入失败,请查看导入失败的课程");
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {// 关闭流
			try {
			    if(ouputStream!=null){
                    ouputStream.close();
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "import/index";
	}
}
