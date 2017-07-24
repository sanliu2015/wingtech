package com.ts.core.db.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map; 

import org.hibernate.metadata.ClassMetadata;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;

public interface IHibernateService extends Serializable{
	public HibernateTemplate getHibernateTemplate();
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate);
	public JdbcTemplate getJdbcTemplate();
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	public Serializable saveObject(Object entity);
	public Serializable saveObjectNoCreateInfo(Object entity, RequestContext requestContext);
	public Serializable saveObject(Object entity,RequestContext requestContext); 
	public Serializable saveObjectAndId(Object entity,RequestContext requestContext);
	public void flush();
	public int generateTableId(String tableName,RequestContext requestContext); 
	public Object updateObject(Object entity,RequestContext requestContext); 
	public Object mergeObject(Object entity,RequestContext requestContext);
	 
	public <T> T getObject(Class<T> objClass,Serializable id,RequestContext requestContext); 
	public void deleteList(Collection<?> entities,RequestContext requestContext);
	public void deleteObject(Object entity,RequestContext requestContext);  
	public List queryHqlForList(String sql );
	public List queryHqlForList(String hql,RequestContext requestContext );
	public <T> T queryHqlForBean(String hql,RequestContext requestContext );
	public List queryHqlForList(String hql,Map<String,Object> parameterMap,int pageNo,  int pageSize);
	public List queryHqlForList(String hql, int pageNo,int pageSize,final  Object... values);
	public List queryHqlForList(String hql,Map<String,Object> parameterMap, int pageNo,int pageSize,final RequestContext requestContext,final  Object... values);
	public Integer queryHqlForInt(final String hql);
	public <T> T queryHqlForObject(final String hql, RequestContext requestContext); 
	public int executeUpdateHql(String hql,RequestContext requestContext);
	//原生sql语句操作
	public Integer queryForInt(final String sql); 
	public IArrayData getArray();
	
	public List queryForList(String sql,RequestContext requestContext);
	public List queryForList(final String sql, final int position,final int length);
	public List queryForList(final String sql, Map<String,Object> parameterMap,final int pageNo, final int pageSize);
	public List queryForList(final String sql,  final int pageNo, final int pageSize,final  Object... values);
	
	public <T> T queryForObject(final String sql,Class<T> requiredType); 
	 
	
	public int executeUpdate(final String sql); 
	public List executeSqlToList(final String sql, final int position,final int length,boolean removeNull,boolean rowIsMapOrArray,final Object[] returnParams,
			RowMapper rowMapper,RequestContext requestContext);
	
	public List executeSqlToList(final String sql);
	public List executeSqlToList(final String sql,RequestContext requestContext);
	public List executeSqlToList(final String sql,  final Object[] returnParams);
	public List executeSqlToList(final String sql, final int position,final int length,final Object[] returnParams);
	 
	public void executeProcedureCall(final String sql,Map<String,Object> para,Object... values);
	 
	 
	 
	public void queryIdToName(final String sql,  Object bean,RequestContext requestContext) ;
	
	public static interface IArrayData{
		public List queryForList(String sql);
		public List queryForList(final String sql, final int position,final int length); 
		public List queryForList(final String sql, Map<String,Object> parameterMap,final int pageNo, final int pageSize);
		public List queryForList(final String sql,  final int pageNo, final int pageSize,final  Object... values);
		
		public List executeSqlToArrayList(final String sql);
		public List executeSqlToArrayList(final String sql,  final Object[] returnParams);
	}
	
	public IDatabaseMeta getDatabaseMeta();
	
	public static interface IDatabaseMeta{
		public String getDatabaseName();
		public List getAllTableSchema();
		public Map<String,ClassMetadata> getAllEntitys();
		public List getTableColumns(final String table) ;
		public String[] getPrimaryKeys(String catalog,String schema,String table);
		public   void getAccountsDbName(Map map,RequestContext requestContext);
	}
	public IStoreProcedure sp();
	public static interface IStoreProcedure{
		Map<String, Object> call(final String sql,List<CallableParameter> params,Object... values);
	}
	
	/**
	 * @author plq
	 * <p> add 2015-12-12
	 * @param sql
	 * @param objs
	 * @return
	 */
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs);
	
	public int getCountForJdbcParam(String sql, Object... objs);
	
	public Integer executeSqlForJdbc(String sql, Object... objs);
	
	public int[] batchUpdate(String sql, List<Object[]> objs); 
}
