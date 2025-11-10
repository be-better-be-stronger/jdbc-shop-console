package com.demo.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.jdbc.dao.ProductDao;
import com.demo.jdbc.model.Category;
import com.demo.jdbc.model.Product;
import com.demo.jdbc.util.DB;

public class ProductDaoImpl implements ProductDao{
	private static final Logger log = LoggerFactory.getLogger(ProductDaoImpl.class.getName());
	
	private Product map(ResultSet rs) throws Exception {
        Category c = new Category(rs.getInt("c_id"), rs.getString("c_name"));
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getInt("quantity"),
                c
        );
    }

	@Override
	public List<Product> findAll() {
		String sql = """
	            SELECT p.id, p.name, p.price, p.quantity,
	                   c.id AS c_id, c.name AS c_name
	            FROM products p
	            JOIN categories c ON p.category_id = c.id
	            ORDER BY p.id
	        """;
	        List<Product> list = new ArrayList<>();
	        try (Connection cn = DB.getConnection();
	             PreparedStatement ps = cn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) list.add(map(rs));
	        } catch (Exception e) {
	            throw new RuntimeException("Find all products failed", e);
	        }
	        return list;
	}

	@Override
	public Optional<Product> findById(int id) {
		String sql = """
	            SELECT p.id, p.name, p.price, p.quantity,
	                   c.id AS c_id, c.name AS c_name
	            FROM products p
	            JOIN categories c ON p.category_id = c.id
	            WHERE p.id=?
	        """;
	        try (Connection cn = DB.getConnection();
	             PreparedStatement ps = cn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) return Optional.of(map(rs));
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Find product failed", e);
	        }
	        return Optional.empty();
	}

	@Override
	public Product insert(Product p) {
		String sql = "INSERT INTO products(name, price, quantity, category_id) VALUES(?, ?, ?, ?)";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setInt(4, p.getCategory().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getInt(1));
            }
            return p;
        } catch (Exception e) {
            throw new RuntimeException("Insert product failed", e);
        }
	}

	@Override
	public boolean update(Connection cn, Product p) {
		String sql = "UPDATE products SET name=?, price=?, quantity=?, category_id=? WHERE id=?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setInt(4, p.getCategory().getId());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Update product failed", e);
        }
	}

	@Override
	public boolean deleteById(int id) {
		String sql = "DELETE FROM products WHERE id=?";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Delete product failed", e);
        }
	}

	@Override
	public List<Product> findByCategoryId(int categoryId) {
		String sql = """
	            SELECT p.id, p.name, p.price, p.quantity,
	                   c.id AS c_id, c.name AS c_name
	            FROM products p
	            JOIN categories c ON p.category_id = c.id
	            WHERE c.id=?
	        """;
	        List<Product> list = new ArrayList<>();
	        try (Connection cn = DB.getConnection();
	             PreparedStatement ps = cn.prepareStatement(sql)) {
	            ps.setInt(1, categoryId);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) list.add(map(rs));
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Find products by category failed", e);
	        }
	        return list;
	}

	@Override
	public boolean existsInOrderItems(int productId) {
		String sql = "SELECT COUNT(*) FROM order_items WHERE product_id=?";
	    try (Connection cn = DB.getConnection();
	         PreparedStatement ps = cn.prepareStatement(sql)) {
	        ps.setInt(1, productId);
	        log.debug("Checking if product {} exists in order_items", productId);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	            	int count = rs.getInt(1);
	            	boolean exists = count > 0;
	            	log.debug("Product {} exists in order_items: {} ({} records found)", 
	            			productId, exists, count);
	            	return exists;
	            }
	        }
	    } catch (SQLException e) {
            log.error("SQL error while checking product usage (productId={})", productId, e);
            throw new RuntimeException("Database error when checking product usage", e);
        } catch (Exception e) {
            log.error("Unexpected error while checking product usage (productId={})", productId, e);
            throw new RuntimeException("Unexpected error when checking product usage", e);
        }

        log.debug("Product {} not found in order_items", productId);
	    return false;
	}
}
