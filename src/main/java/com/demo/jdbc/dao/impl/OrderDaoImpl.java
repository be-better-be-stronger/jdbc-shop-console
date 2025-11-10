package com.demo.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.demo.jdbc.dao.OrderDao;
import com.demo.jdbc.model.Customer;
import com.demo.jdbc.model.Order;
import com.demo.jdbc.model.OrderItem;
import com.demo.jdbc.model.Product;
import com.demo.jdbc.util.DB;

public class OrderDaoImpl implements OrderDao {

	@Override
	public Order insert(Connection cn, Order order) {
		String sql = "INSERT INTO orders(customer_id, order_date, total) VALUES (?, ?,? )";
        try ( PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getCustomer().getId());
            ps.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            ps.setBigDecimal(3, order.getTotal());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) order.setId(rs.getInt(1));
            }
            return order;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("Insert order failed", e);            
        }
	}

	@Override
	public Optional<Order> findById(int id) {
		String sql = """
	            SELECT o.id, o.order_date, o.total, c.id AS c_id, c.name AS c_name, c.email AS c_email
	            FROM orders o
	            JOIN customers c ON o.customer_id = c.id
	            WHERE o.id=?
	        """;
	        try (Connection cn = DB.getConnection();
	             PreparedStatement ps = cn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    Customer cus = new Customer(rs.getInt("c_id"), rs.getString("c_name"), rs.getString("c_email"));
	                    Order order = new Order(rs.getInt("id"), cus, rs.getTimestamp("order_date").toLocalDateTime(), rs.getBigDecimal("total"));
	                    return Optional.of(order);
	                }
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Find order failed", e);
	        }
	        return Optional.empty();
	}

	@Override
	public List<Order> findAllWithDetails() {
		String sql = """
		        SELECT o.id AS order_id, o.order_date, o.total,
		               c.id AS c_id, c.name AS c_name, c.email AS c_email,
		               oi.id AS item_id, oi.quantity, oi.price,
		               p.id AS p_id, p.name AS p_name
		        FROM orders o
		        JOIN customers c ON o.customer_id = c.id
		        JOIN order_items oi ON o.id = oi.order_id
		        JOIN products p ON oi.product_id = p.id
		        ORDER BY o.id, oi.id
		    """;
		    List<Order> orders = new ArrayList<>();
		    try (Connection cn = DB.getConnection();
		         PreparedStatement ps = cn.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {

		        Order currentOrder = null;
		        int lastOrderId = -1;

		        while (rs.next()) {
		            int orderId = rs.getInt("order_id");
		            if (orderId != lastOrderId) {
		                Customer cus = new Customer(
		                        rs.getInt("c_id"),
		                        rs.getString("c_name"),
		                        rs.getString("c_email")
		                );
		                currentOrder = new Order(
		                        orderId,
		                        cus,
		                        rs.getTimestamp("order_date").toLocalDateTime(),
		                        rs.getBigDecimal("total")
		                );
		                currentOrder.setItems(new ArrayList<>());
		                orders.add(currentOrder);
		                lastOrderId = orderId;
		            }

		            // Thêm từng OrderItem vào danh sách
		            if (currentOrder != null) {
		                Product p = new Product(
		                        rs.getInt("p_id"),
		                        rs.getString("p_name"),
		                        rs.getBigDecimal("price"),
		                        0,
		                        null
		                );
		                OrderItem item = new OrderItem(
		                        rs.getInt("item_id"),
		                        p,
		                        rs.getInt("quantity"),
		                        rs.getBigDecimal("price")
		                );
		                currentOrder.getItems().add(item);
		            }
		        }
		    } catch (Exception e) {
		        throw new RuntimeException("Find orders with details failed", e);
		    }
		    return orders;
	}

}
