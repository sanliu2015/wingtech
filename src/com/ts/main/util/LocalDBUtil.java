package com.ts.main.util;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

public class LocalDBUtil {
	public final Logger log = Logger.getLogger(this.getClass());

	private static BasicDataSource ds = null;
	//使用ThreadLocal存储当前线程中的Connection对象
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	

  //在静态代码块中创建数据库连接池
    static{
        try{
            //通过代码创建C3P0数据库连接池
        	ds = new BasicDataSource();
        	String pathUrl = LocalDBUtil.class.getClassLoader().getResource("").getPath();
			pathUrl = pathUrl.substring(0, pathUrl.length() - 8) + "/config/localDB.properties";
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
	
	
	
//	public static void executeProc(String procName) throws SQLException {
//		Connection conn = getConnection();
//		CallableStatement call = conn.prepareCall("{call " + procName + "}");
//		try {
//			call.execute();
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			close(); 
//		}
//	}
//	
//	
//	// 对数据库的增加、修改和删除的操作
//	public static boolean executeUpdate(String sql) {
//		Connection con = getConnection();
//		try {
//			Statement stmt = con.createStatement();
//			int iCount = stmt.executeUpdate(sql); // 执行参数SQL语句
////			System.out.println("操作成功，所影响的记录数为" + String.valueOf(iCount)); // 在控制台中显示执行的结果
//			return true; // 如果SQL语句执行成功，返回true
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false; // 如果SQL语句执行失败，返回false
//		} finally {
//			close(); 
//		}
//	}
//
//	// 对数据库的查询操作
//	public static ResultSet executeQuery(String sql) {
//		ResultSet rs; // 设置ResultSet结果集对象rs
//		try {
//			Connection con = getConnection();
//			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			try {
//				rs = stmt.executeQuery(sql); // 执行参数中的SQL语句
//			} catch (SQLException e) {
//				e.printStackTrace();
//				return null;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			close(); 
//		}
//		return rs; // 将查询的结果通过return关键字进行返回
	// }	
	
//     /**
//      * 测试
//      * @param args
//      * @throws SQLException
//      */
//     public static void main(String[] args) throws SQLException {
//    	try {
//    		LocalDBUtil.startTransaction();
//    		QueryRunner qr = new QueryRunner(LocalDBUtil.getDataSource());
//    		int n1 = qr.update(LocalDBUtil.getConnection(),"insert into plq_test(name,age) select getDate(),10");
//         	int n2 = qr.update(LocalDBUtil.getConnection(),"insert into plq_test(name,age) select getDate(),'错误'");
//         	LocalDBUtil.commit();
//    	} catch(Exception e) {
//    		e.printStackTrace();
//    		LocalDBUtil.rollback();
//    	} finally { 
//    		LocalDBUtil.close();
//    	}
//     	
//     	
//     }
     
     
}