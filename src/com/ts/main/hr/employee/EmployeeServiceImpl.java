package com.ts.main.hr.employee;
    
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.ts.core.api.cache.SimpleCachePool;
import com.ts.core.common.bean.OperatePromptBean; 
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.core.util.ReflectUtils;

@Service("employeeSerivce")
public class EmployeeServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass()); 
	public void addEmployee(RequestContext requestContext , IBaseServiceManger service,EmployeeForm form){
		  
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,EmployeeForm form){
		 service.getDb().saveObject(form.getBean(),requestContext);  
		 DefaultMultipartHttpServletRequest mvcRequest = (DefaultMultipartHttpServletRequest) requestContext.getRequest();
		 MultipartFile file = mvcRequest.getFile("photoFile");
		 if (!file.isEmpty()) {
			 String filePath = requestContext.getRequest().getSession().getServletContext().getRealPath("/") + "/upload/employee/"  
                     + form.getBean().getNumber() + ".jpg";  
             // 转存文件  
             try {
				file.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setBillId(form.getBean().getId() );
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public void editEmployee(RequestContext requestContext , IBaseServiceManger service,EmployeeForm form){
		Employee bean=service.getDb().getObject(Employee.class, form.getId(),requestContext); 
		form.setBean(bean); 
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,EmployeeForm form){
		Employee bean=service.getDb().getObject(Employee.class, form.getBean().getId(),requestContext); 
		 BeanUtils.copyNoNullProperties(form.getBean(), bean, "interimId");
		 service.getDb().updateObject(bean,requestContext);  
		 DefaultMultipartHttpServletRequest mvcRequest = (DefaultMultipartHttpServletRequest) requestContext.getRequest();
		 MultipartFile file = mvcRequest.getFile("photoFile");
		 if (!file.isEmpty()) {
			 String filePath = requestContext.getRequest().getSession().getServletContext().getRealPath("/") + "/upload/employee/"  
                     + form.getBean().getNumber() + ".jpg";  
             // 转存文件  
             try {
				file.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success);  
		 return prompt;
	}
	 
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,EmployeeForm form){ 
		OperatePromptBean prompt=new OperatePromptBean();
		Employee bean=service.getDb().getObject(Employee.class, form.getId(),requestContext);  
		StringBuilder sql = new StringBuilder("select 1 from dorm_checkIn where employeeId=").append(form.getId());
		List<Map> rs = service.getDb().queryForList(sql.toString(), requestContext);
		if (rs != null && rs.size() > 0) {
			prompt.setError("有入住信息不能删除！");
		} else {
			service.getDbService().deleteObject(bean,requestContext); 
		}
		prompt.setId(bean.getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
	
	public void refreshEmployeeId(RequestContext requestContext, IBaseServiceManger service, EmployeeForm form) {
		requestContext.getRequest().setAttribute("id", form.getId());
	}
	
	public OperatePromptBean doRefreshEmployeeId(RequestContext requestContext, IBaseServiceManger service) {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		String empId = requestContext.getRequest().getParameter("empId");
		String sp = "dorm_ReSetEmpId(?,?)";
		List<CallableParameter> paraList = new ArrayList<CallableParameter>();
		CallableParameter param1 = new CallableParameter(); 
		param1.setParameterValue(yearMonth);
		param1.setSqlParameter(new SqlParameter("yearMonth", java.sql.Types.VARCHAR));
		paraList.add(param1);
		CallableParameter param2 = new CallableParameter(); 
		param2.setParameterValue(empId);
		param2.setSqlParameter(new SqlParameter("empId", java.sql.Types.VARCHAR));
		paraList.add(param2);
		service.getDb().sp().call(sp, paraList);
		OperatePromptBean prompt = new OperatePromptBean();
		return prompt;
	}
	  
}
