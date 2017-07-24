package com.ts.core.db.support;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.jdbc.core.RowMapper; 
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;  

import com.ts.core.db.bean.FieldSchema;
import com.ts.core.db.service.IJdbcService;

@Repository("jdbcService")
public class JdbcServiceSupport  implements IJdbcService {
	@Autowired
	public JdbcTemplate jdbcTemplate;
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public  List   queryForList(String sql ){    
	
		return jdbcTemplate.queryForList(sql);
	}
	
	@SuppressWarnings("unchecked")
	public  List   queryForList(String sql,int pageNo,int pageSize,RowMapper rowMapper){   
		int position=(pageNo-1)*pageSize;  
		return (List)jdbcTemplate.execute(new ConnectionCallback(){ 
			@Override
			public Object doInConnection(Connection con) throws SQLException, DataAccessException {   
                Statement st = con.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);  
                ResultSet rs=st.executeQuery(sql);  
                List<FieldSchema> fields=DataBaseUtil.getDbFieldSchemas(rs.getMetaData()); 
				if(position>1) rs.absolute(position );  
				int rowNum = 0; 
				List rows=new ArrayList(); 
				while (rs.next()) {  
					if (pageSize>0 && rowNum>=pageSize)   break;  
					if(rowMapper!=null){
						Object row=rowMapper.mapRow(rs, rowNum);
						rows.add(row);
					} else {
						Map row=new LinkedHashMap();
						for(int i=0;i<fields.size();i++){
							String key=fields.get(i ).getFieldAliasName(); 
							row.put(key, rs.getObject(key));  
						}
						rows.add(row);
					}
					++rowNum;  
				} 
				if (rs!=null) rs.close();
                if (st!=null) st.close();
				return rows;
			}
			
		});
		/*return (List)jdbcTemplate.executeUpdate(new StatementCallback (){ 
			@Override
			public Object doInStatement(Statement st) throws SQLException,
					DataAccessException {    
				ResultSet rs=st.executeQuery(sql);
				List<FieldSchema> fields=getDbFieldSchemas(rs.getMetaData());
				if(position>1)
					rs.absolute(position ); 
				rs.getStatement().setFetchDirection(ResultSet.TYPE_SCROLL_INSENSITIVE);
				 
				int rowNum = 0; 
				List rows=new ArrayList();
				while (rs.next()) { 
					++rowNum; 
					if (rowNum > pageSize)  { 
						break  ; 
					}   
					Map row=new HashMap();
					for(int i=0;i<fields.size();i++){
						String key=fields.get(i ).getFieldAliasName(); 
						row.put(key, rs.getObject(key));  
					}
					rows.add(row);
					 
				} 
				rs.close();
				return rows;
			}
			
		});*/
		/*return (List) jdbcTemplate.query(sql, new SplitPageResultSetExtractor(new RowMapper<UserBean>(){
			public UserBean mapRow(ResultSet rs,int index) throws SQLException{
				UserBean user=new UserBean();
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("userName"));
				user.setLastIp(rs.getString("lastIp"));
				return user;
			}
		},position,pageSize));  */
	}
	public  List   queryForList(String sql,int pageNo,int pageSize,RowMapper rowMapper,Map parameterMap){
		 
		return null;
	}
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(final String sql,Map paramMap,Class<T> requiredType){
		Object obj=namedParameterJdbcTemplate.queryForObject(sql, paramMap, requiredType);
		return (T)obj;
	}
	public KeyHolder executeSqlForReturnKey(String sql,Map parameterMap){
		KeyHolder keyholder=new GeneratedKeyHolder();    
		namedParameterJdbcTemplate.update(sql,  new MapSqlParameterSource(parameterMap), keyholder);
		 
		return keyholder; 
	}
	public int executeSql(String sql,Map parameterMap ){   
		return namedParameterJdbcTemplate.update(sql, parameterMap); 
	}
	public Map<String, Object> excuteStoreProcedure(final String sql,List<CallableParameter> params){
		 List<SqlParameter> paramList = new ArrayList<SqlParameter>();  
		 for(CallableParameter p:params){
			 paramList.add(p.getSqlParameter());
		 }
		 Map<String, Object> outValues = jdbcTemplate.call(  
	      new CallableStatementCreator() {  
	        @Override  
	        public CallableStatement createCallableStatement(Connection conn) throws SQLException {  
	          CallableStatement cstmt = conn.prepareCall(sql);  
	          for(int i=1;i<=params.size();i++){
	        	  CallableParameter p=params.get(i-1);
	        	  if(p.getSqlParameter().getClass()==SqlOutParameter.class){
	        		  cstmt.registerOutParameter(i, p.getSqlParameter().getSqlType());
	        	  } else if(p.getSqlParameter().getClass()==SqlInOutParameter.class){
	        		  cstmt.registerOutParameter(i, p.getSqlParameter().getSqlType());
	        	  }
	        	  if(p.getParameterValue()!=null){ 
	        		  cstmt.setObject(i, p.getParameterValue());
	        	  }
	          }
	         // cstmt.registerOutParameter(1, Types.VARCHAR);  
	         // cstmt.registerOutParameter(2, Types.DECIMAL);  
	          //cstmt.setString(1, mtlNo);  
	          return cstmt;  
	    }}, paramList);  
		 return outValues;
	}
	 
	
	 
}
