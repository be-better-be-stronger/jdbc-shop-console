package com.demo.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    /** Bắt đầu transaction */
    public static void begin() throws SQLException {
        Connection cn = DB.getConnection();
        cn.setAutoCommit(false);
        connectionHolder.set(cn);
    }

    /** Lấy connection hiện tại */
    public static Connection getConnection() throws SQLException {
        Connection cn = connectionHolder.get();
        if (cn == null) {
            cn = DB.getConnection(); // fallback auto commit
            connectionHolder.set(cn);
        }
        return cn;
    }

    /** Commit transaction */
    public static void commit() throws SQLException {
        Connection cn = connectionHolder.get();
        if (cn != null) {
            cn.commit();
            cn.close();
            connectionHolder.remove();
        }
    }

    /** Rollback transaction */
    public static void rollback() {
        try {
            Connection cn = connectionHolder.get();
            if (cn != null) {
                cn.rollback();
                cn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionHolder.remove();
        }
    }
}
