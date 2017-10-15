package com.ts.main.sys.schedulejob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ts.core.common.constant.Globals;
import com.ts.core.context.AppContext;
import com.ts.core.db.service.IHibernateService;
import com.ts.main.dorm.init.MyInitTaskService;
import com.ts.main.util.HrorcDbUtil;
import com.ts.main.util.KqDbUtil;
import com.ts.main.util.LocalDBUtil;

import nl.justobjects.pushlet.util.Log;


/**
 * 后台定时任务业务类
 * @author plq
 *
 */
public class ExecuteTask {
	
	private static String queryJob = "";	// 职位
	private static String queryDep = "";	// 部门
	private static String queryCop = "";	// 公司
	private static String queryLab = "";	// 劳务
	private static String queryEmp = "";
	private static String queryTempEmp = "";
	private static String queryKq = "";		// 考勤查询
	private static String queryPhoto = "";
	static {
		try {
        	String pathUrl = ExecuteTask.class.getClassLoader().getResource("").getPath();
			pathUrl = pathUrl.substring(0, pathUrl.length() - 8) + "/config/hrorcDB.properties";
			Properties prop = new Properties();
			prop.load(new FileInputStream(pathUrl));
			queryJob = prop.getProperty("queryJob").trim();
			queryDep = prop.getProperty("queryDep").trim();
			queryCop = prop.getProperty("queryCop").trim();
			queryLab = prop.getProperty("queryLab").trim();
			queryEmp = prop.getProperty("queryEmp").trim();
			queryTempEmp = prop.getProperty("queryTempEmp").trim();
			queryPhoto = prop.getProperty("queryPhoto").trim();
			queryKq = prop.getProperty("queryKq").trim();
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	// 同步公司
	public void syncCop(Integer jobId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qr = new QueryRunner(HrorcDbUtil.getDataSource());
		try {
			List<Object[]> list = (List<Object[]>) qr.query(HrorcDbUtil.getConnection(), queryCop, new ArrayListHandler());
			if (list != null && list.size() > 0) {
				QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
				try {
					LocalDBUtil.startTransaction();
					qrLocal.update(LocalDBUtil.getConnection(), "truncate table Im_company");
					Object[][] params = new Object[list.size()][];
					for (int i=0,len=list.size(); i<len; i++) {
						params[i] = list.get(i);
					}
					qrLocal.batch(LocalDBUtil.getConnection(), "insert into Im_company(name,forShort,number,parentNumber) values(?,?,?,?)", params);
					qrLocal.update(LocalDBUtil.getConnection(), "exec ipexl_company_after");
					LocalDBUtil.commit();
				} catch(Exception e) {
					e.printStackTrace();
					Log.error(e.getMessage());
		    		LocalDBUtil.rollback();
		    		// 插入出错日志
		    		LocalDBUtil.startTransaction();
		    		Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"同步公司信息失败",jobId,e.getMessage()});
		    		LocalDBUtil.commit();
		    	} finally { 
		    		LocalDBUtil.close();
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 同步部门
	public void syncDep(Integer jobId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qr = new QueryRunner(HrorcDbUtil.getDataSource());
		try {
			List<Object[]> list = (List<Object[]>) qr.query(HrorcDbUtil.getConnection(), queryDep, new ArrayListHandler());
			if (list != null && list.size() > 0) {
				QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
				try {
					LocalDBUtil.startTransaction();
					qrLocal.update(LocalDBUtil.getConnection(), "truncate table Im_department");
					Object[][] params = new Object[list.size()][];
					for (int i=0,len=list.size(); i<len; i++) {
						params[i] = list.get(i);
					}
					qrLocal.batch(LocalDBUtil.getConnection(), "insert into Im_department(name,number,parentNumber,compNumber,shortName) values(?,?,?,?,?)", params);
					qrLocal.update(LocalDBUtil.getConnection(), "exec ipexl_department_after");
					LocalDBUtil.commit();
				} catch(Exception e) {
					e.printStackTrace();
					Log.error(e.getMessage());
		    		LocalDBUtil.rollback();
		    		// 插入出错日志
		    		LocalDBUtil.startTransaction();
		    		Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"同步部门信息失败",jobId,e.getMessage()});
		    		LocalDBUtil.commit();
		    	} finally { 
		    		LocalDBUtil.close();
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void syncJob(Integer jobId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qr = new QueryRunner(HrorcDbUtil.getDataSource());
		try {
			List<Object[]> list = (List<Object[]>) qr.query(HrorcDbUtil.getConnection(), queryJob, new ArrayListHandler());
			if (list != null && list.size() > 0) {
				QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
				try {
					LocalDBUtil.startTransaction();
					qrLocal.update(LocalDBUtil.getConnection(), "truncate table Im_position");
					Object[][] params = new Object[list.size()][];
					for (int i=0,len=list.size(); i<len; i++) {
						params[i] = list.get(i);
					}
					qrLocal.batch(LocalDBUtil.getConnection(), "insert into Im_position(name,number) values(?,?)", params);
					qrLocal.update(LocalDBUtil.getConnection(), "exec ipexl_position_after");
					LocalDBUtil.commit();
				} catch(Exception e) {
					e.printStackTrace();
					Log.error(e.getMessage());
		    		LocalDBUtil.rollback();
		    		// 插入出错日志
		    		LocalDBUtil.startTransaction();
		    		Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"同步职位信息失败",jobId,e.getMessage()});
		    		LocalDBUtil.commit();
		    	} finally { 
		    		LocalDBUtil.close();
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void syncLab(Integer jobId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qr = new QueryRunner(HrorcDbUtil.getDataSource());
		try {
			List<Object[]> list = (List<Object[]>) qr.query(HrorcDbUtil.getConnection(), queryLab, new ArrayListHandler());
			if (list != null && list.size() > 0) {
				QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
				try {
					LocalDBUtil.startTransaction();
					qrLocal.update(LocalDBUtil.getConnection(), "truncate table Im_labour");
					Object[][] params = new Object[list.size()][];
					for (int i=0,len=list.size(); i<len; i++) {
						params[i] = list.get(i);
					}
					qrLocal.batch(LocalDBUtil.getConnection(), "insert into Im_labour(name,number) values(?,?)", params);
					qrLocal.update(LocalDBUtil.getConnection(), "exec ipexl_labour_after");
					LocalDBUtil.commit();
				} catch(Exception e) {
					e.printStackTrace();
					Log.error(e.getMessage());
		    		LocalDBUtil.rollback();
		    		// 插入出错日志
		    		LocalDBUtil.startTransaction();
		    		Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"同步劳务公司信息失败",jobId,e.getMessage()});
		    		LocalDBUtil.commit();
		    	} finally { 
		    		LocalDBUtil.close();
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void autoScore(Integer jobId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
		try {
			LocalDBUtil.startTransaction();
			qrLocal.update(LocalDBUtil.getConnection(), "exec dorm_autoScore");
			LocalDBUtil.commit();
		} catch(Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
    		LocalDBUtil.rollback();
    		// 插入出错日志
    		LocalDBUtil.startTransaction();
    		try {
				Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"报修自动评分失败",jobId,e.getMessage()});
				LocalDBUtil.commit();
    		} catch (SQLException e1) {
				LocalDBUtil.rollback();
				Log.error(e.getMessage());
			}
    	} finally { 
    		LocalDBUtil.close();
    	}
	}
	
	public void syncEmp(Integer jobId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qr = new QueryRunner(HrorcDbUtil.getDataSource());
		try {
			List<Object[]> list = (List<Object[]>) qr.query(HrorcDbUtil.getConnection(), queryEmp, new ArrayListHandler());
			if (list != null && list.size() > 0) {
				System.out.println(list.size());
				QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
				try {
					LocalDBUtil.startTransaction();
					qrLocal.update(LocalDBUtil.getConnection(), "truncate table Im_employee");
					Object[][] params = new Object[list.size()][];
					for (int i=0,len=list.size(); i<len; i++) {
						params[i] = list.get(i);
					}
					qrLocal.batch(LocalDBUtil.getConnection(), "insert into Im_employee(name,number,sex,depNo,jobNo,idNo,labourNo,status,outDate,phone,leaveReason,inDate) values(?,?,?,?,?,?,?,?,?,?,?,?)", params);
					qrLocal.update(LocalDBUtil.getConnection(), "exec ipexl_employee_after");
					LocalDBUtil.commit();
				} catch(Exception e) {
					e.printStackTrace();
					Log.error(e.getMessage());
		    		LocalDBUtil.rollback();
		    		// 插入出错日志
		    		LocalDBUtil.startTransaction();
		    		Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"同步员工信息失败",jobId,e.getMessage()});
		    		LocalDBUtil.commit();
		    	} finally { 
		    		LocalDBUtil.close();
		    	}
			} else {
				System.out.println("没查到结果");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 临时人员
	public void syncTempEmp(Integer jobId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qr = new QueryRunner(HrorcDbUtil.getDataSource());
		try {
			List<Object[]> list = (List<Object[]>) qr.query(HrorcDbUtil.getConnection(), queryTempEmp, new ArrayListHandler());
			if (list != null && list.size() > 0) {
				System.out.println(list.size());
				QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
				try {
					LocalDBUtil.startTransaction();
					qrLocal.update(LocalDBUtil.getConnection(), "truncate table Im_tempEmployee");
					Object[][] params = new Object[list.size()][];
					for (int i=0,len=list.size(); i<len; i++) {
						params[i] = list.get(i);
					}
					qrLocal.batch(LocalDBUtil.getConnection(), "insert into Im_tempEmployee(name,number,sex,depNo,jobNo,idNo,labourNo,inDate) values(?,?,?,?,?,?,?,?)", params);
					qrLocal.update(LocalDBUtil.getConnection(), "exec ipexl_tempEmployee_after");
					LocalDBUtil.commit();
				} catch(Exception e) {
					e.printStackTrace();
					Log.error(e.getMessage());
		    		LocalDBUtil.rollback();
		    		// 插入出错日志
		    		LocalDBUtil.startTransaction();
		    		Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"同步临时员工信息失败",jobId,e.getMessage()});
		    		LocalDBUtil.commit();
		    	} finally { 
		    		LocalDBUtil.close();
		    	}
			} else {
				System.out.println("没查到结果");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void syncKq(Integer jobId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qr = new QueryRunner(KqDbUtil.getDataSource());
		try {
			List<Object[]> list = (List<Object[]>) qr.query(KqDbUtil.getConnection(), queryKq, new ArrayListHandler());
			if (list != null && list.size() > 0) {
				QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
				try {
					LocalDBUtil.startTransaction();
					qrLocal.update(LocalDBUtil.getConnection(), "truncate table im_kq");
					Object[][] params = new Object[list.size()][];
					for (int i=0,len=list.size(); i<len; i++) {
						params[i] = list.get(i);
					}
					qrLocal.batch(LocalDBUtil.getConnection(), "insert into im_kq(empNumber,icNumber) values(?,?)", params);
					qrLocal.update(LocalDBUtil.getConnection(), "exec ipexl_kq_after");
					LocalDBUtil.commit();
				} catch(Exception e) {
					e.printStackTrace();
					Log.error(e.getMessage());
		    		LocalDBUtil.rollback();
		    		// 插入出错日志
		    		LocalDBUtil.startTransaction();
		    		Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"同步考勤信息失败",jobId,e.getMessage()});
		    		LocalDBUtil.commit();
		    	} finally { 
		    		LocalDBUtil.close();
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 同步照片
	public void syncPhoto(Integer jobId) {
		String currentPath = ExecuteTask.class.getClassLoader().getResource("").getPath();
		String photoPath = currentPath.replace("WEB-INF/classes/", "upload/employee/");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String occurTime = df.format(new Date());
		QueryRunner qr = new QueryRunner(HrorcDbUtil.getDataSource());
		try {
			Statement stmt;
			stmt = HrorcDbUtil.getDataSource().getConnection().createStatement();
			// 执行数据库查询语句
			ResultSet rs = stmt.executeQuery(queryPhoto);
			while (rs.next()) {
//				System.out.println(rs.getString("eid"));
				int tmpi=0;
				OutputStream out = new FileOutputStream(photoPath + rs.getString("CARDNUM") + ".jpg");
				InputStream ins= rs.getBlob("CAP_IMG").getBinaryStream();
				while((tmpi=ins.read())!=-1){
					out.write(tmpi);
				}
				ins.close();
				out.flush();
				out.close();
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
    		try {
    			// 插入出错日志
        		LocalDBUtil.startTransaction();
				Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(occurDate,infoFlag,jobId,description) values(?,?,?,?)", new ArrayHandler(), new Object[]{occurTime,"同步照片信息出错",jobId,e.getMessage()});
				LocalDBUtil.commit();
    		} catch (SQLException e1) {
				e1.printStackTrace();
			} finally { 
	    		LocalDBUtil.close();
	    	}
    		
		} finally {
			HrorcDbUtil.close();
    	}
	}
	
	
	/**
	 * 测试出错
	 * @param args
	 */
	public static void main(String[] args) {
		QueryRunner qr = new QueryRunner(HrorcDbUtil.getDataSource());
		QueryRunner qrLocal = new QueryRunner(LocalDBUtil.getDataSource());
    	try {
//			List<Map<String, Object>> list = (List<Map<String, Object>>) qr.query(HrorcDbUtil.getConnection(), queryJob, new MapListHandler());
			List<Object[]> list = (List<Object[]>) qr.query(HrorcDbUtil.getConnection(), queryJob, new ArrayListHandler());
			if (list != null && list.size() > 0) {
				try {
					LocalDBUtil.startTransaction();
					qrLocal.update(LocalDBUtil.getConnection(), "truncate table Im_position");
					Object[][] params = new Object[list.size()][];
					for (int i=0,len=list.size(); i<len; i++) {
						params[i] = list.get(i);
					}
					qrLocal.batch(LocalDBUtil.getConnection(), "insert into Im_position(number,name) values(?,?)", params);
					List<Object[]> a = qrLocal.query(LocalDBUtil.getConnection(), "select id,name from Im_position", new ArrayListHandler());
					System.out.println(a.size());
					qrLocal.update(LocalDBUtil.getConnection(), "exec ipexl_position_after");
					int b = 3/0;
					LocalDBUtil.commit();
				} catch(Exception e) {
		    		e.printStackTrace();
		    		LocalDBUtil.rollback();
		    		LocalDBUtil.startTransaction();
		    		Object[] insertRs = qrLocal.insert(LocalDBUtil.getConnection(), "insert into Sys_ScheduleJobLog(jobId,description) values(?,?)", new ArrayHandler(), new Object[]{1,e.getMessage()});
		    		System.out.println("ArrayHandler: " + Arrays.toString(insertRs));
		    		LocalDBUtil.commit();
		    	} finally { 
		    		LocalDBUtil.close();
		    	}
				
			}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
