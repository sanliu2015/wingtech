package com.ts.main.dorm.empenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;

@Service("empEnterService")
public class EmpEnterServiceImpl implements IAppService {

	private static String queryEmpSql = "select isnull(a.status,'0') as status,a.name,a.number,a.idCard,b.name depName,c.name parentDepName,d.name posName,e.name gender from HR_Employee a WITH(NOLOCK) left join HR_Department b WITH(NOLOCK) on a.deptId=b.id left join HR_Department c WITH(NOLOCK) on b.parentId=c.id left join HR_Position d WITH(NOLOCK) on a.positionId=d.id left join TS_SysCode e WITH(NOLOCK) on e.codeKind=27 and a.gender=e.code where a.icNo=? order by a.id desc ";
	private static String queryIcLost = "select id from Dorm_IcLost WITH(NOLOCK) where icNo=?";
	
	
	public void init(RequestContext requestContext, IBaseServiceManger service) {
		List<Map<String, Object>> doorList = service.getDb().findForJdbc("select code,name from ts_syscode where codekind=320");
		requestContext.getRequest().setAttribute("doorList", doorList);
	}
	
	public String doCheck(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		String door = requestContext.getRequest().getParameter("door");
		String icNo = requestContext.getRequest().getParameter("icNo");
		List<Map<String, Object>> lostList = service.getDb().findForJdbc(queryIcLost, new Object[]{icNo});
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (lostList != null && lostList.size() > 0) {
			resultMap.put("success", "0");
			resultMap.put("errorMsg", "此卡已经挂失，无效卡");
		} else {
			List<Map<String, Object>> empList = service.getDb().findForJdbc(queryEmpSql, new Object[]{icNo});
			if (empList != null && empList.size() > 0) {
				resultMap.putAll(empList.get(0));
				String status = "" + resultMap.get("status");
				if ("0".equals(status)) {
					resultMap.put("success", "0");
					resultMap.put("errorMsg", "此卡对应人员已离职，无效卡");
				} else {
					resultMap.put("success", "1");
					EmpEnter bean = new EmpEnter();
					bean = JSON.parseObject(JSON.toJSONString(resultMap), EmpEnter.class);
					bean.setIcNo(icNo);
					bean.setDoor(door);
					service.getDb().saveObject(bean);
				}
			} else {
				resultMap.put("success", "0");
				resultMap.put("errorMsg", "找不到此卡对应信息");
			}
		}
		
		
		requestContext.getResponse().getWriter().write(JSON.toJSONString(resultMap));
		requestContext.getResponse().getWriter().flush();
		requestContext.getResponse().getWriter().close();
		return null;
	}
	
}
