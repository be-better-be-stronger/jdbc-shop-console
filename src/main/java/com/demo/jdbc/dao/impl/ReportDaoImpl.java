package com.demo.jdbc.dao.impl;

import com.demo.jdbc.dao.ReportDao;
import com.demo.jdbc.model.Report;
import com.demo.jdbc.util.DB;
import com.demo.jdbc.util.TransactionManager;

import java.sql.*;
import java.util.*;

public class ReportDaoImpl implements ReportDao {

    @Override
    public List<Report> revenueByProduct() {
        String sql = """
            SELECT p.name AS name,
                   SUM(oi.quantity) AS quantity,
                   SUM(oi.quantity * oi.price) AS revenue
            FROM order_items oi
            JOIN products p ON oi.product_id = p.id
            GROUP BY p.name
            ORDER BY revenue DESC;
        """;
        return queryReport(sql);
    }

    @Override
    public List<Report> revenueByCustomer() {
        String sql = """
            SELECT c.name AS name,
                   COUNT(o.id) AS quantity,
                   SUM(o.total) AS revenue
            FROM orders o
            JOIN customers c ON o.customer_id = c.id
            GROUP BY c.name
            ORDER BY revenue DESC;
        """;
        return queryReport(sql);
    }

    // helper
    private List<Report> queryReport(String sql) {
        List<Report> list = new ArrayList<>();
        try {
        	Connection cn = TransactionManager.getConnection();
        	try (
                    PreparedStatement ps = cn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                   while (rs.next()) {
                       list.add(new Report(
                           rs.getString("name"),
                           rs.getLong("quantity"),
                           rs.getBigDecimal("revenue")
                       ));
                   }
               }
        }catch (Exception e) {
            throw new RuntimeException("Query report failed", e);
        }
        return list;
    }
}
