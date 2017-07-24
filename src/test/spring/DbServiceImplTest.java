package test.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional; 

import com.ts.core.db.service.IHibernateService;
import com.ts.core.db.service.IJdbcService;
import com.ts.core.system.user.UserBean;
import com.ts.core.util.DateTimeUtil;
import com.ts.core.util.ReflectUtils;

@RunWith(SpringJUnit4ClassRunner.class) 
//加载spring配置文件  
@ContextConfiguration(locations={"classpath:/test/resources/tomkysoft-hibernate.xml"}) 
public class DbServiceImplTest  extends AbstractTransactionalJUnit4SpringContextTests{
	   
	@Autowired 
    private  IHibernateService hDbService; 
	@Autowired 
    private  IJdbcService dbService; 
	
	
	public IJdbcService getDbService() {
		return dbService;
	}

	public void setDbService(IJdbcService dbService) {
		this.dbService = dbService;
	}

	public IHibernateService gethDbService() {
		return hDbService;
	}

	public void sethDbService(IHibernateService hDbService) {
		this.hDbService = hDbService;
	}

	//@Transactional //这个测试用例在一个事物中执行,执行完回回滚不会破坏数据库   
	//@Test
    public void testHDbService()  {  
    	String currentMethodName=ReflectUtils.traceMethodName(Thread.currentThread().getStackTrace());
		System.out.println( currentMethodName+"......");
        StringBuffer sql=new StringBuffer();
        sql.append(" select count(a) from UserBean as a ");
        int records=hDbService.queryForObject(sql.toString(),Integer.class);
        System.out.println(records);
        sql.setLength(0);
        sql.append(" select a.id from UserBean as a where a.id=3");
        Integer r=hDbService.queryHqlForObject(sql.toString(), null);
        System.out.println(r);
         
        sql.setLength(0);
        sql.append(" select * from ts_User");
        List list=hDbService.getArray().queryForList(sql.toString(),2,4);
        for(int i=0;i<list.size();i++){
        	Object[] row=(Object[])list.get(i);
        	System.out.println(i+"="+row[0]);
        }
        sql.setLength(0);
        sql.append(" select a from UserBean as a");
        List<UserBean> lt=hDbService.queryHqlForList(sql.toString(),2,4);
        for(int i=0;i<lt.size();i++){
	        UserBean user=lt.get(i);
	        System.out.println(i+":"+user.getUserId());
        }
        sql.setLength(0);
        sql.append(" update   UserBean  set name='a' where id=3 ");
        int result=hDbService.executeUpdateHql(sql.toString(),null);
        System.out.println( "executeHql result="+result);
        UserBean row=hDbService.getObject(UserBean.class, new Integer(3),null);
        System.out.println( "row="+row.getName());
        sql.setLength(0);
        sql.append(" update   ts_User  set name='abc' where id=4 ");
        result=hDbService.executeUpdate(sql.toString());
        System.out.println( "executeSql result="+result);
        row=hDbService.getObject(UserBean.class, new Integer(4),null);
        System.out.println( "row="+row.getName());
        sql.setLength(0);
        sql.append(" select a from  UserBean as a where a.name=:name ");
        Map<String,Object> map=new HashMap<String,Object>(); 
        map.put("createTime", "2013-02-22");
       // map.put("id", 4);
        lt=hDbService.queryHqlForList(sql.toString(), map,0,0);
        System.out.println( "row="+lt.get(0).getName());
        sql.setLength(0);
        sql.append(" select a from  UserBean as a where a.id=? ");
        lt=hDbService.queryHqlForList(sql.toString(),0,0,new Integer(6));
        System.out.println( "row2="+lt.get(0).getName());
    }  
    @Transactional //这个测试用例在一个事物中执行,执行完回回滚不会破坏数据库   
	@Test
    public void testHDbServiceQueryForObject()  { 
		String currentMethodName=ReflectUtils.traceMethodName(Thread.currentThread().getStackTrace());
		System.out.println( currentMethodName+"......");
		StringBuffer sql=new StringBuffer(); 
        sql.append(" select name from ts_User  where id=1 ");
        String rec=hDbService.queryForObject(sql.toString(),String.class);
        System.out.println(  "rec="+rec);
        sql.setLength(0);
        sql.append(" select a.id from UserBean as a where a.id=3");
        Integer r=hDbService.queryHqlForObject(sql.toString(), null);
        System.out.println(r);
	} 
	@Transactional //这个测试用例在一个事物中执行,执行完回回滚不会破坏数据库   
	@Test
    public void testQueryForList()  { 
		String currentMethodName=ReflectUtils.traceMethodName(Thread.currentThread().getStackTrace());
		System.out.println( currentMethodName+"......");
		StringBuffer sql=new StringBuffer();
        sql.append(" select * from ts_User ");
        List rows=dbService.queryForList(sql.toString());
        for(int i=0;i<rows.size();i++){
        	Map row=(Map)rows.get(i);
        	System.out.println(row.get("name"));
        } 
	}
	@Transactional //这个测试用例在一个事物中执行,执行完回回滚不会破坏数据库   
	@Test
    public void testQueryForListPage()  { 
		String currentMethodName=ReflectUtils.traceMethodName(Thread.currentThread().getStackTrace());
		System.out.println( currentMethodName+"......");
		StringBuffer sql=new StringBuffer(); 
        sql.append(" select * from ts_User   ");
        List<Map> list=dbService.queryForList(sql.toString(),2,6,null);
        for(int i=0;i<list.size();i++){
        	Map row= list.get(i);
        	System.out.println("row.getName()"+row.get("id"));
        }
	}
	@Transactional //这个测试用例在一个事物中执行,执行完回回滚不会破坏数据库   
	@Test
    public void testDbServiceQueryForObject()  { 
		String currentMethodName=ReflectUtils.traceMethodName(Thread.currentThread().getStackTrace());
		System.out.println( currentMethodName+"......");
		StringBuffer sql=new StringBuffer(); 
        sql.append(" select name from ts_User  where id=1 ");
        String rec=dbService.queryForObject(sql.toString(),null,String.class);
        System.out.println(  "rec="+rec);
	} 
	 
}
