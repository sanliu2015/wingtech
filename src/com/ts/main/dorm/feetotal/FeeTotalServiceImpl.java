package com.ts.main.dorm.feetotal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;

@Service("feeTotalService")
public class FeeTotalServiceImpl implements IAppService {

	public void showTotal(RequestContext requestContext, IBaseServiceManger service) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> buildList = service.getDb().findForJdbc("select id,name from Dorm_Building where kind='0' ");
		requestContext.getRequest().setAttribute("buildList", JSON.toJSONString(buildList));
	}
	
	public String queryTotal(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		List<Map<String, Object>> deptList = service.getDb().findForJdbc("" + requestContext.getMessageResource().get("queryDept"), yearMonth);
		List<Map<String, Object>> buidList = service.getDb().findForJdbc("" + requestContext.getMessageResource().get("queryBuid"), yearMonth);
		String queryTotal = "" + requestContext.getMessageResource().get("queryTotal");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		for (Map<String, Object> deptMap : deptList) {
		    Map<String, Object> tempMap = new HashMap<String, Object>();
		    tempMap.put("deptName", deptMap.get("deptName"));
		    BigDecimal wtpwgsTotal = BigDecimal.ZERO;
		    BigDecimal sharedTotal = BigDecimal.ZERO;
		    BigDecimal corpTotal = BigDecimal.ZERO;
		    BigDecimal rentTotal = BigDecimal.ZERO;
		    
		    for (Map<String, Object> buildMap : buidList) {
		        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		        if ("0".equals(deptMap.get("id").toString())) {
	                resultList = service.getDb().findForJdbc(queryTotal + " and d.id is null ", new Object[]{yearMonth, buildMap.get("buildingId")});
	            } else {
	                resultList = service.getDb().findForJdbc(queryTotal + " and d.id=? ", new Object[]{yearMonth, buildMap.get("buildingId"), deptMap.get("id")});
	            }
		        if (resultList != null && resultList.size() > 0) {
		            tempMap.put(buildMap.get("buildingId")+"wtpwgsFee", resultList.get(0).get("wtpwgsFee") == null ? 0 : resultList.get(0).get("wtpwgsFee"));
                    tempMap.put(buildMap.get("buildingId")+"sharedFee", resultList.get(0).get("sharedFee") == null ? 0 : resultList.get(0).get("sharedFee"));
                    tempMap.put(buildMap.get("buildingId")+"corpFee", resultList.get(0).get("corpFee") == null ? 0 : resultList.get(0).get("corpFee"));
                    tempMap.put(buildMap.get("buildingId")+"rentFee", resultList.get(0).get("rentFee") == null ? 0 : resultList.get(0).get("rentFee"));
                    if (resultList.get(0).get("wtpwgsFee") != null) {
                        wtpwgsTotal = wtpwgsTotal.add(new BigDecimal("" + resultList.get(0).get("wtpwgsFee")));
                    }
                    if (resultList.get(0).get("sharedFee") != null) {
                        sharedTotal = sharedTotal.add(new BigDecimal("" + resultList.get(0).get("sharedFee")));
                    }
                    if (resultList.get(0).get("corpFee") != null) {
                        corpTotal = corpTotal.add(new BigDecimal("" + resultList.get(0).get("corpFee")));
                    }
                    if (resultList.get(0).get("rentFee") != null) {
                        rentTotal = rentTotal.add(new BigDecimal("" + resultList.get(0).get("rentFee")));
                    }
                    
		        }
		    }
		    tempMap.put("wtpwgsTotal", wtpwgsTotal);
		    tempMap.put("sharedTotal", sharedTotal);
		    tempMap.put("corpTotal", corpTotal);
		    tempMap.put("rentTotal", rentTotal);
		    dataList.add(tempMap);
		}
		
		Map<String, Object> dataGridMap = new HashMap<String, Object>();
		dataGridMap.put("total", dataList.size());
		dataGridMap.put("rows", dataList);
		
		try {
			requestContext.getResponse().getWriter().write(JSON.toJSONString(dataGridMap));
			requestContext.getResponse().getWriter().flush();
			requestContext.getResponse().getWriter().close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	
}
