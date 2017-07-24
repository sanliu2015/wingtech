package com.ts.main.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class KqDbUtil {

	private static BasicDataSource ds = null;
	//使用ThreadLocal存储当前线程中的Connection对象
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    
    //在静态代码块中创建数据库连接池
    static{
    	try{
            //通过代码创建C3P0数据库连接池
        	ds = new BasicDataSource();
        	String pathUrl = KqDbUtil.class.getClassLoader().getResource("").getPath();
			pathUrl = pathUrl.substring(0, pathUrl.length() - 8) + "/config/kqDB.properties";
			Properties prop = new Properties();
			prop.load(new FileInputStream(pathUrl));
        	ds.setDriverClassName(prop.getProperty("driver_class").trim());
            ds.setUrl(prop.getProperty("url").trim());
            ds.setUsername(prop.getProperty("username").trim());
            ds.setPassword(prop.getProperty("password").trim());
            ds.setInitialSize(Integer.valueOf(prop.getProperty("initialSize").trim()));
            ds.setMaxActive(Integer.valueOf(prop.getProperty("maxActive").trim()));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
    * @Method: getConnection
    * @Description: 从数据源中获取数据库连接
    * @return Connection
    * @throws SQLException
    */ 
    public static Connection getConnection() throws SQLException{
        //从当前线程中获取Connection
        Connection conn = threadLocal.get();
        if(conn==null){
            //从数据源中获取数据库连接
            conn = getDataSource().getConnection();
            //将conn绑定到当前线程
            threadLocal.set(conn);
        }
        return conn;
    }
    
    /**
    * @Method: startTransaction
    * @Description: 开启事务
    *
    */ 
    public static void startTransaction(){
        try{
            Connection conn =  threadLocal.get();
            if(conn==null){
                conn = getConnection();
                 //把 conn绑定到当前线程上
                threadLocal.set(conn);
            }
            //开启事务
            conn.setAutoCommit(false);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: rollback
    * @Description:回滚事务
    
    *
    */ 
    public static void rollback(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                //回滚事务
                conn.rollback();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: commit
    * @Description:提交事务
    *
    */ 
    public static void commit(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                //提交事务
                conn.commit();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: close
    * @Description:关闭数据库连接(注意，并不是真的关闭，而是把连接还给数据库连接池)
    *
    */ 
    public static void close(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                conn.close();
                 //解除当前线程上绑定conn
                threadLocal.remove();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: getDataSource
    * @Description: 获取数据源
    * @return DataSource
    */ 
    public static DataSource getDataSource(){
        //从数据源中获取数据库连接
        return ds;
    }
    
    public static void main(String[] args) {
    	QueryRunner qr = new QueryRunner(KqDbUtil.getDataSource());
    	String sql = "select * from users where id=?";
    	try {
			List<Map<String, Object>> list = (List<Map<String, Object>>) qr.query(sql, new MapListHandler());
			System.out.println(list.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
}
