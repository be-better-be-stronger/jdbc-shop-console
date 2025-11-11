package com.demo.jdbc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	private static String url;
    private static String user;
    private static String pass;
    
    static {
        try (InputStream is = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
        	if (is == null) {
                System.err.println("⚠️ Không tìm thấy file cấu hình: db.properties");
                System.exit(1);
            }else {
            	Properties p = new Properties();
                p.load(is);
                url = p.getProperty("db.url");
                user = p.getProperty("db.username");
                pass = p.getProperty("db.password");
            }    
        	// ✅ Thử kết nối ngay khi khởi động
            try (Connection testConn = DriverManager.getConnection(url, user, pass)) {
//                System.out.println("Kết nối Database thành công!");
            } catch (SQLException e) {
                System.err.println("Không thể kết nối đến Database:");
                System.err.println("->" + e.getMessage());
                System.exit(1); // Dừng chương trình gọn gàng
            }
        } catch (Exception e) {
        	System.err.println("Không thể đọc file cấu hình DB: " + e.getMessage());
        	System.err.println("->" + e.getMessage());
            System.exit(1);
        }
    }

    public static Connection getConnection() throws SQLException {
    	return DriverManager.getConnection(url, user, pass);
    }
}
