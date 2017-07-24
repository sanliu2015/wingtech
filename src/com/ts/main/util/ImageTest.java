package com.ts.main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.sql.BLOB;

public class ImageTest {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "insert into ePhoto(eid,photo) values(?,?)";
		try {
			// 加载数据库驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException ex) {
			System.out.println("数据库驱动加载失败！");
		}
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
		try {
			// 连接数据库
			conn = DriverManager.getConnection(url, "c##plq", "plq1210");
//			pst = conn.prepareStatement(sql);
//            pst.setInt(1, 1);
//            pst.setBlob(2, BLOB.empty_lob());  //插入空对象empty_blob()  
//            int ii = pst.executeUpdate();

            OutputStream os = null;
            String q_sql = "select eid,photo from ePhoto where eid = ? for update"; // 锁定数据行进行更新  
            pst = conn.prepareStatement(q_sql);
            pst.setInt(1, 1);
            rs = pst.executeQuery();
            if (rs.next()) {
                int tmpi=0;
				OutputStream out = new FileOutputStream("d:/"  + rs.getString("eid") + ".jpg");
				InputStream ins= rs.getBlob("photo").getBinaryStream();
				while((tmpi=ins.read())!=-1){
					out.write(tmpi);
				}
				ins.close();
				out.flush();
				out.close();
            }
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库连接失败");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
