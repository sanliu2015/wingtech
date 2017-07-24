package com.ts.core.db.support;

import java.io.Serializable; 
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection; 
import java.util.Iterator;  
import java.util.List; 
import java.util.Map;

import javax.persistence.ParameterMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.transform.Transformers; 
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;  
import org.springframework.stereotype.Repository;   

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject; 
import com.ts.core.api.cache.SimpleCachePool;
import com.ts.core.api.rules.TableRuleImpl; 
import com.ts.core.common.constant.Globals; 
import com.ts.core.context.RequestContext; 
import com.ts.core.context.messageresource.QueryBeanSqlListInvokeEvent;
import com.ts.core.db.service.IField; 
import com.ts.core.db.service.IHibernateService;   
import com.ts.core.db.service.IHibernateService.IStoreProcedure;

@Repository("dbService")
public class HibernateServiceSupport  implements IHibernateService {
	private static final Log log = LogFactory.getLog(HibernateServiceSupport.class);
	@Autowired
	public HibernateTemplate hibernateTemplate;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public HibernateTemplate getHibernateTemplate() {  
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public Serializable  saveObject(Object entity){ 
		return saveObject(entity,null);
	}
	public Serializable saveObject(Object entity,RequestContext requestContext){
		DataBaseUtil.setCreateDate(entity);
		DataBaseUtil.setSaveOperater(entity,requestContext);
		TableRuleImpl.generateNumber(entity, requestContext, this, null);
		int needSave=UploadFileServiceSupport.uploadFile(entity,requestContext);
		//if(needSave==-1) return null;
		DataBaseUtil.savetEntityCache(entity);
		return hibernateTemplate.save(entity); 
	}
	
	public Serializable saveObjectNoCreateInfo(Object entity,RequestContext requestContext){
//		DataBaseUtil.setCreateDate(entity);
//		DataBaseUtil.setSaveOperater(entity,requestContext);
		TableRuleImpl.generateNumber(entity, requestContext, this, null);
		int needSave=UploadFileServiceSupport.uploadFile(entity,requestContext);
		//if(needSave==-1) return null;
		DataBaseUtil.savetEntityCache(entity);
		return hibernateTemplate.save(entity); 
	}
	
	public int generateTableId(String tableName,RequestContext requestContext){
		 return GenerateTableIdServiceSupport.generateClassNameId(tableName,null,requestContext,this);
	}
	 
	 
	public Serializable saveObjectAndId(Object entity,RequestContext requestContext){ 
		GenerateTableIdServiceSupport.generateClassNameId(null,entity,requestContext,this);
		return saveObject(entity,requestContext);  
	}
	public Serializable saveObjectAndId(Object entity){   
        return saveObjectAndId(entity,null); 
	}
	public void flush(){
		hibernateTemplate.flush();
	}
	public Object updateObject(Object entity,RequestContext requestContext){
		DataBaseUtil.setUpdateOperater(entity,requestContext);
		UploadFileServiceSupport.uploadFile(entity, requestContext);
		DataBaseUtil.updateEntityCache(entity);
		hibernateTemplate.update(entity);
		return entity;
	} 
	public Object mergeObject(Object entity,RequestContext requestContext){
		DataBaseUtil.setUpdateOperater(entity,requestContext); 
		UploadFileServiceSupport.uploadFile(entity, requestContext);
		DataBaseUtil.updateEntityCache(entity);
		return hibernateTemplate.merge(entity);
	}
	 
	public void deleteObject(Object entity,RequestContext requestContext){
		if(entity==null) return ;
		if(entity instanceof List){
			this.deleteList((List)entity, requestContext);
			return ;
		}
		UploadFileServiceSupport.deleteUploadFile(entity, requestContext);
		DataBaseUtil.deleteEntityCache(entity);
		hibernateTemplate.delete(entity);
	}
	 
	 
	public void deleteList(Collection<?> entities,RequestContext requestContext){
		if(entities!=null && entities.size()>0){
			Object[] objs=entities.toArray() ;
			if(UploadFileServiceSupport.validateFileBean(objs[0])){
				for(int i=0;i<objs.length;i++)
				  UploadFileServiceSupport.deleteUploadFile(objs[i], null);
			}
			hibernateTemplate.deleteAll(entities);
			DataBaseUtil.deleteEntitysCache(entities);
		}
	}
	/**
	 * 延时加载对象
	 * @param entity
	 */
	public void initializeObject(Object entity){
		  hibernateTemplate.initialize(entity);
	}
	 
	public <T> T getObject(Class<T> objClass,Serializable id,RequestContext requestContext){
		if(id==null) return null;    	 
        T bean= getHibernateTemplate().get(objClass,id);
        QueryBeanSqlListInvokeEvent.execute(requestContext, null, this, null, bean, null, null);
        //DataBaseUtil.queryAttributeSqlDataToBean(bean, requestContext, this); 
        return bean;
	}
	public  <T> T   queryHqlForBean(String hql,RequestContext requestContext ){ 
		List list=queryHqlForList(hql,requestContext);
		if(list!=null && list.size()>0){
			return  (T)list.get(0);
		} else return null;
	}
	public  List   queryHqlForList(String hql ){  
		return queryHqlForList(hql,null);
	} 
	public  List   queryHqlForList(String hql,RequestContext requestContext ){  
		List lt=hibernateTemplate.find(hql); 
		if(lt!=null && lt.size()>0 && requestContext!=null){
			for(int i=0;i<lt.size();i++){
				Object bean=lt.get(i);
				if(bean.getClass().isArray()==false){
					Object value=QueryBeanSqlListInvokeEvent.execute(requestContext, null, this, null, bean, null, null);
					if(value==null) break;
				}
			}
		}
		return lt;
	} 
	public  List   queryHqlForList(String hql,  int pageNo,  int pageSize){  
		return queryHqlForList(hql,null,pageNo,pageSize,null); 
	}  
	public List queryHqlForList(String hql,Map<String,Object> parameterMap,int pageNo,  int pageSize){
		return queryHqlForList(hql,parameterMap,pageNo,pageSize,null); 
	}
	public List queryHqlForList(String hql, int pageNo,int pageSize,final  Object... values){ 
		return queryHqlForList(hql,null,pageNo,pageSize,null,values); 
	}
	
	@SuppressWarnings("unchecked")
	public List queryHqlForList(String hql,Map<String,Object> parameterMap, int pageNo,int pageSize,final RequestContext requestContext,final  Object... values){
		List lt = (List)getHibernateTemplate().execute(new HibernateCallback() { 
			public Object doInHibernate(Session session) throws HibernateException { 
			Query query = session.createQuery(hql); 
			if(parameterMap!=null){
				Iterator<String> iter=parameterMap.keySet().iterator();
				while (iter.hasNext()){
					String key=iter.next();
					query.setParameter(key, parameterMap.get(key));
				}
			} 
			if(values!=null){
				 for(int i=0;i<values.length;i++){
					 query.setParameter(i, values[i]);
				 }
			} 
			if(pageNo!=0 && pageSize!=0){
				int position=(pageNo-1)*pageSize;
				query.setFirstResult(position).setMaxResults(pageSize);  
			}
			List list = query.list();  
			return list; 
		}}); 
		if(lt==null) lt=new ArrayList();
		if(lt!=null && lt.size()>0 && requestContext!=null){
			for(int i=0;i<lt.size();i++){
				Object bean=lt.get(i);
				if(bean.getClass().isArray()==false){
					Object value=QueryBeanSqlListInvokeEvent.execute(requestContext, null, this, null, bean, null, null);
					if(value==null) break;
				}
			}
		}
		return lt;
	}
	@SuppressWarnings("unchecked")
	public  Object findHqlUnique( final  String hql ) {    
        return  getHibernateTemplate().execute( new  HibernateCallback() {  
            public  Object doInHibernate(Session s)  
                    throws  HibernateException {  
            	 Query query = s.createQuery(hql);    
                return  query.uniqueResult();  
            }  
        });   
    }
	@SuppressWarnings("unchecked")
	public <T> T queryHqlForObject(final String hql, RequestContext requestContext){
		 return  (T)getHibernateTemplate().execute( new  HibernateCallback() {  
	            public  Object doInHibernate(Session s)  throws  HibernateException {  
	            	 Query query = s.createQuery(hql);    
	                return  query.uniqueResult();  
	            }  
	        });   
	}
	public Integer queryHqlForInt(final String hql){ 
       List lt=queryHqlForList(hql );
       if (lt!=null &&lt.size()>0 && lt.get(0)!=null){
    	   if (lt.get(0) instanceof Integer)
    		   return  (Integer)lt.get(0) ; 
    	   else  if (lt.get(0) instanceof Long)
    		   return  ((Long)lt.get(0)).intValue(); 
       }
       return 0;
	}
	 
	@SuppressWarnings("unchecked")
	public int executeUpdateHql(String hql,RequestContext requestContext){
		Integer result = (Integer)getHibernateTemplate().execute(new HibernateCallback() { 
			public Object doInHibernate(Session session) throws HibernateException {  
			Query  query= session.createQuery(hql); 
			Integer value=query.executeUpdate(); 
			return value; }});  
		 return result;  
	}
	 public List queryForList(String sql,RequestContext requestContext){
		 return queryForList(sql, 0,0);
	 }
	 public List queryForList(final String sql, final int pageNo, final int pageSize) { 
		return queryForList(sql,pageNo,pageSize,null);  
	 }
	 public List queryForList(final String sql, Map<String,Object> parameterMap,final int pageNo, final int pageSize) { 
		 return queryForList(sql,true,parameterMap,pageNo,pageSize,null); 
	 }
	 public List queryForList(final String sql,  final int pageNo, final int pageSize,final  Object... values) { 
		return queryForList(sql,true,null,pageNo,pageSize,values); 
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List queryForList(final String sql,boolean rowIsMapOrArray, Map<String,Object> parameterMap,final int pageNo, final int pageSize,final  Object... values) { 
		  
		return queryForDataList(sql,rowIsMapOrArray,parameterMap,pageNo,pageSize,values); 
	 }
	 @SuppressWarnings("unchecked")
	public void executeProcedureCall(final String sql,Map<String,Object> para,Object... values){
		 getHibernateTemplate().execute(new HibernateCallback() { 
				public Object doInHibernate(Session session) throws HibernateException { 
					 
				ProcedureCall p=session.createStoredProcedureCall(sql);
				p.registerParameter0(1, String.class, ParameterMode.IN);
				p.registerParameter0(1, BigDecimal.class, ParameterMode.OUT);
				 
				 p.extractMemento(para);
				 Object s= p.getOutputs().getOutputParameterValue(0);
				  return null; }}); 
	 }
	 @SuppressWarnings("unchecked")
	 private List queryForDataList(final String sql,boolean rowIsMapOrArray, Map<String,Object> parameterMap,final int pageNo, final int pageSize,final  Object... values) { 
		 List lt = (List)getHibernateTemplate().execute(new HibernateCallback() { 
			public Object doInHibernate(Session session) throws HibernateException { 
			SQLQuery  query= session.createSQLQuery(sql);  
			 
			if(rowIsMapOrArray)
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
			if(parameterMap!=null){
				Iterator<String> iter=parameterMap.keySet().iterator();
				while (iter.hasNext()){
					String key=iter.next();
					query.setParameter(key, parameterMap.get(key));
				}
			} 
			if(values!=null){
				 for(int i=0;i<values.length;i++){
					 query.setParameter(i, values[i]);
				 }
			} 
			if(pageNo!=0 && pageSize!=0){
				int position=(pageNo-1)*pageSize;
				query.setFirstResult(position).setMaxResults(pageSize);  
			} 
			List list = query.list();   
			return list; }}); 
		if(lt==null) lt=new ArrayList();
		return lt; 
	 }
	 @SuppressWarnings("unchecked")
	 public void queryIdToName(String param, Object bean,RequestContext requestContext) {  
		 Object p=JSON.parse(param );
		 if(p instanceof JSONArray){
			 JSONArray arr=(JSONArray)p;
			 for(int i=0;i<arr.size();i++){
				 JSONObject o=(JSONObject)arr.get(i);
				 DataBaseUtil.setMapToObject(o, this, bean,requestContext); 
			 }
		 } else {
			 JSONObject o=(JSONObject)p;
			 DataBaseUtil.setMapToObject(o, this, bean,requestContext);  
		 }
		 
	 }
	 
	 @SuppressWarnings("unchecked")
	 public  Object findUnique( final  String sql ) {   
        return  getHibernateTemplate().execute( new  HibernateCallback() {  
            public  Object doInHibernate(Session s)  
                    throws  HibernateException {  
            	 SQLQuery query = s.createSQLQuery(sql);  
                return  query.uniqueResult();  
            }  
        });   
    }
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(final String sql,Class<T> requiredType){
		 return  (T)getHibernateTemplate().execute( new  HibernateCallback() {  
	            public  Object doInHibernate(Session s)  
	                    throws  HibernateException {  
	            	 SQLQuery query = s.createSQLQuery(sql);    
	                return  query.uniqueResult();  
	            }  
	        });   
	}
	 @SuppressWarnings("unchecked")
	 public int executeUpdate (final String sql) { 
		 Integer result = (Integer)getHibernateTemplate().execute(new HibernateCallback() { 
			public Object doInHibernate(Session session) throws HibernateException {  
			SQLQuery  query= session.createSQLQuery(sql);  
			Integer value=query.executeUpdate(); 
			return value; }});  
		 return result; 
	 }
	  
	 public Integer queryForInt(final String sql){ 
       List lt=this.queryForList(sql,false,null,0,0,null); 
       if (lt!=null &&lt.size()>0 && lt.get(0)!=null){
    	   if (lt.get(0) instanceof Integer)
    		   return  (Integer)lt.get(0) ;
    	   else  if (lt.get(0) instanceof Long)
    		   return  ((Long)lt.get(0)).intValue(); 
    	   
       }
       return 0;
	 }
	
	   
	public  List   executeSqlToList(final String sql, final int position,final int length,boolean removeNull,boolean rowIsMapOrArray,final Object[] returnParams,RowMapper rowMapper,RequestContext requestContext){
		@SuppressWarnings("unchecked")
		Object list=this.getHibernateTemplate().execute(new HibernateCallback() { 
			@Override
			public Object doInHibernate(Session session) throws HibernateException   {
				List  rows = new ArrayList(); 
				session.doWork( new Work() {  
				    public void execute(Connection con)throws java.sql.SQLException   {   
				    	Statement statement=null;
		                ResultSet rs=null;  
		                if (session.getTransaction()!=null)
		                    session.getTransaction().setTimeout(Globals.TIMEOUT);  
		                try {
		                   statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		                           ResultSet.CONCUR_READ_ONLY); 
		                   statement.setQueryTimeout(Globals.TIMEOUT);
		                   rs = statement.executeQuery(sql);     
		                   int cols = rs.getMetaData().getColumnCount();
		                   List<IField> fieldList=new ArrayList<IField>(); 
		                   DataBaseUtil.converMetaDataToFields(rs.getMetaData(),fieldList,null);
		                   if (returnParams!=null && returnParams.length>0){  
			                   returnParams[0]=fieldList; 
			                   if(returnParams.length>1)
			                	   returnParams[1]=rs.getMetaData();
		                   } 
		                	int startPosition=(position<0?0:position);
							int records=(length<=0?Integer.MAX_VALUE:length);   
		                    int n = 1; 
		                    boolean haveRecord=true;
		                    if(startPosition>0) 
		                    	haveRecord=rs.absolute(startPosition+1); 
		                    else
		                    	haveRecord=rs.absolute(1);
		                    while ( haveRecord && n <= records) {
		                    	if(rowMapper!=null){
		                    		Object row=rowMapper.mapRow(rs, n);
		    						rows.add(row);
		                    	} else {
		                    	  if(rowIsMapOrArray==true){
			                          Map record=DataBaseUtil.mapRowHander(rs,fieldList,removeNull,cols); 
			                          rows.add(record);
		                    	  } else {
		                    		  Object[] recordArray =DataBaseUtil.arrayRowHander(rs,fieldList,removeNull,cols);  
		                              rows.add(recordArray);
		                    	  } 
		                       }
		                       n++;
		                       if (rs.isLast())   break; 
		                       rs.next();
		                    } 
		                }  finally {
		                    if (rs!=null)
							try {
								rs.close();
								if (statement!=null)
									statement.close(); 
							} catch (SQLException e) { 
								e.printStackTrace();
							}
		                      
		                }
				      }  
				    } );
				 
                return rows;
			}  
		});
		 return (List)list;  
	}
	 
	@Override
	public List executeSqlToList(String sql) { 
		return executeSqlToList(sql,0,0,false,true,null,null,null);
	}
	@Override
	public List executeSqlToList(String sql,RequestContext requestContext) { 
		return executeSqlToList(sql,0,0,false,true,null,null,requestContext);
	}
	@Override
	public List  executeSqlToList(String sql, int position, int length,Object[] returnParams) { 
		return executeSqlToList(sql,position,length,false,true,returnParams,null,null);
	}
	@Override
	public List  executeSqlToList(String sql, Object[] returnParams) {
		return executeSqlToList(sql,0,0,false,true,returnParams,null,null);
	}
	
	private IArrayData arrayData=new ArrayData(); 
	public IArrayData getArray(){
		 return arrayData;
	}
	public   class  ArrayData implements IArrayData {  
		@Override
		public List queryForList(String sql){
			 return queryForList(sql, 0,0);
		 }
		@Override
		 public List queryForList(final String sql, final int pageNo, final int pageSize) { 
			return queryForDataList(sql,false,null,pageNo,pageSize,null);  
		 }
		 @Override
		 public List queryForList(final String sql, Map<String,Object> parameterMap,final int pageNo, final int pageSize) { 
			 return queryForDataList(sql,false,parameterMap,pageNo,pageSize,null); 
		 }
		 @Override
		 public List queryForList(final String sql,  final int pageNo, final int pageSize,final  Object... values) { 
			return queryForDataList(sql,false,null,pageNo,pageSize,values); 
		 }
		 @Override
		public List executeSqlToArrayList(final String sql){
			return executeSqlToList(sql,0,0,false,false,null,null,null);
		}
		@Override
		public List executeSqlToArrayList(final String sql, Object[] returnParams){
			return executeSqlToList(sql,0,0,false,false,returnParams,null,null);
		}
	}
	 
	 private DatabaseMeta databaseMeta=new DatabaseMeta();
	 public IDatabaseMeta getDatabaseMeta(){
		 return databaseMeta;
	 }
	  
	public   class  DatabaseMeta implements IDatabaseMeta {  
		@Override
		@SuppressWarnings("unchecked")
		public List getAllTableSchema() {
			return (List)getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session)   throws HibernateException  { 
	            	ResultSet rs=null;
	                List lt = new ArrayList(); 
	                session.doWork( new Work() {  
	                public void execute(Connection con)throws java.sql.SQLException   {  
	                try {  
	                	 DatabaseMetaData dbmd = con.getMetaData();  
	                     String[] types = { "TABLE" };
	                     ResultSet resultSet = dbmd.getTables(null, null, "%", types);
	                     while (resultSet.next()) { 
	                         String tableName = resultSet.getString(3);    
	                         lt.add(tableName);                         
	                       }
	                     }  finally {
	                         if (rs!=null)
	                             rs.close(); 
	                     }
	                }
	                }); 
	                return lt;
	            }
	        });
			
		}

		@Override
		public Map<String, ClassMetadata> getAllEntitys() { 
			Map<String,ClassMetadata> map=getHibernateTemplate().getSessionFactory().getAllClassMetadata();
			return map;
		}
		@SuppressWarnings("unchecked")
		public String   getDatabaseName(){
			 return (String)getHibernateTemplate().execute(new HibernateCallback() {
		            public Object doInHibernate(Session session)  {
		            	  String[] arr=new String[1];
		            	  session.doWork( new Work() {  
		  				    public void execute(Connection con)throws java.sql.SQLException   {     
		                 
			                if (con.isClosed()==true) return  ; 
			                	 /*DatabaseMetaData dbMetaData = con.getMetaData(); 
			                	 Object dbName= ReflectUtils.getFieldValue(dbMetaData,"connection");
			                	 con.getCatalog()
			                	 String s=(String)ReflectUtils.getFieldValue(dbName,"currentDatabase");*/
			                	 arr[0]=con.getCatalog();   
			                     
		  				    }});
		                return arr[0];
		            }
		        });
		}
		@Override
		@SuppressWarnings("unchecked")
		public List getTableColumns(String table) {
			return (List)getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session)   throws HibernateException  { 
	            	List lt = new ArrayList();  
					session.doWork( new Work() {  
					    public void execute(Connection con)throws java.sql.SQLException   {  
					    	Statement stmt =null;  
			                ResultSet rs =null; 
			                try {  
			                	  stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			  	                        ResultSet.CONCUR_READ_ONLY);
			                	  stmt.setQueryTimeout(Globals.TIMEOUT);
			                      rs = stmt.executeQuery("SELECT top 1 * FROM " + table);                     
			                      ResultSetMetaData rsmd = rs.getMetaData();
			                      DataBaseUtil.converMetaDataToFields(rsmd,lt,null);
			                 }  finally {
			                	 if (rs!=null)
			     					try {
			     						rs.close();
			     						if (stmt!=null)
			     							stmt.close(); 
			     					} catch (SQLException e) { 
			     						e.printStackTrace();
			     					}
			                 }
					    } 
					}); 
	                return lt;
	            }
	        });
		} 
		@Override
		@SuppressWarnings("unchecked")
		public String[] getPrimaryKeys(String catalog,String schema,String table) {
			return (String[])getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(Session session)   throws HibernateException  { 
	            	ResultSet rs=null;
	            	String[] array=new String[10];
	                session.doWork( new Work() {  
	                public void execute(Connection con)throws java.sql.SQLException   {  
	                try {  
	                	 DatabaseMetaData dbmd = con.getMetaData(); 
	                	 dbmd.getPrimaryKeys(catalog, schema, table);  
	                     ResultSet resultSet = dbmd.getPrimaryKeys(catalog, schema, table); 
	                     while (resultSet.next()) {  
	                    	 array[0] = resultSet.getString(4); //字段名
	                    	 array[1] = resultSet.getString(6); //主键名   
	                       }
	                     }  finally {
	                         if (rs!=null)
	                             rs.close(); 
	                     }
	                }
	                }); 
	                return array;
	            }
	        });
			
		}
		public   void getAccountsDbName(Map map,RequestContext requestContext){ 
		   String[][] dbs= SimpleCachePool.databases;
		   if(dbs!=null){
			   for(int i=0;i<dbs.length;i++){
				   map.put(dbs[i][0], dbs[i][1]);
			   }
		   } 
		}
	}
	private IStoreProcedure storeProcedure=new StoreProcedure();
	public IStoreProcedure sp(){
		return storeProcedure;
	}
	public   class  StoreProcedure implements  IStoreProcedure{
		@Override
		public Map<String, Object> call(final String sql,List<CallableParameter> params,Object... values){
			 List<SqlParameter> paramList = new ArrayList<SqlParameter>();  
			 if (params != null && params.size() > 0){
				 for(CallableParameter p:params){
					 paramList.add(p.getSqlParameter());
				 }
			 }
			 
			 Map<String, Object> outValues = jdbcTemplate.call(  
		      new CallableStatementCreator() {  
		        @Override  
		        public CallableStatement createCallableStatement(Connection conn) throws SQLException {  
		          CallableStatement cstmt = conn.prepareCall(sql);   
		          if (params != null && params.size() > 0){
		        	  for(int i=0; i<params.size(); i++){
			        	  CallableParameter p=params.get(i);  
			        	  if(p.getSqlParameter().getClass()==SqlOutParameter.class){ 
			        		  cstmt.registerOutParameter( p.getSqlParameter().getName() , p.getSqlParameter().getSqlType());
			        	  } else if(p.getSqlParameter().getClass()==SqlInOutParameter.class){
			        		  cstmt.registerOutParameter( p.getSqlParameter().getName() , p.getSqlParameter().getSqlType());
			        	  }  
			        	  //cstmt.registerOutParameter( p.getSqlParameter().getName() , p.getSqlParameter().getSqlType()); 
			        	  if(p.getParameterValue()!=null){ 
			        		  //cstmt.setObject(i, p.getParameterValue());
			        		  cstmt.setObject(p.getSqlParameter().getName(),p.getParameterValue());
			        	  } else {
			        		  cstmt.setNull(p.getSqlParameter().getName(), p.getSqlParameter().getSqlType());	// plq修改值可为null
			        	  }
			        	  cstmt.getParameterMetaData();
			          }  
		          }
		          return cstmt;  
		    }}, paramList);  
			 return outValues;
		}
	}
	
	
	@Override
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return this.jdbcTemplate.queryForList(sql, objs);
	}
	 
	public int getCountForJdbcParam(String sql, Object... objs) {
		return this.jdbcTemplate.queryForInt(sql, objs);
	}
	
	public Integer executeSqlForJdbc(String sql, Object... objs) {
		return this.jdbcTemplate.update(sql, objs);
	}
	public int[] batchUpdate(String sql,List<Object[]> objs) {
		return jdbcTemplate.batchUpdate(sql, objs);
	}
	
}
