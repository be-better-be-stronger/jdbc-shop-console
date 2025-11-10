package com.demo.jdbc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DB {
	private static String url;
    private static String user;
    private static String pass;
    
    static {
        try (InputStream is = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties p = new Properties();
            p.load(is);
            url = p.getProperty("db.url");
            user = p.getProperty("db.username");
            pass = p.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc file cấu hình DB", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, pass);
    }
}
