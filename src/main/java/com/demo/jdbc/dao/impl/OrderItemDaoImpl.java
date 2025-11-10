package com.demo.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.demo.jdbc.dao.OrderItemDao;
import com.demo.jdbc.model.OrderItem;

public class OrderItemDaoImpl implements OrderItemDao {

	@Override
    public void insertItems(Connection cn, List<OrderItem> items) {
        String sql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            for (OrderItem i : items) {
                ps.setInt(1, i.getOrder().getId());
                ps.setInt(2, i.getProduct().getId());
                ps.setInt(3, i.getQuantity());
                ps.setBigDecimal(4, i.getPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("Insert order items failed", e);
        }
    }

}
