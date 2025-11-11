package com.demo.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.demo.jdbc.dao.CustomerDao;
import com.demo.jdbc.model.Customer;
import com.demo.jdbc.util.DB;
import com.demo.jdbc.util.TransactionManager;

public class CustomerDaoImpl implements CustomerDao{

	private Customer map(ResultSet rs) throws Exception {
        return new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email")
        );
    }
	
	/** Lấy tất cả khách hàng */
	@Override
	public List<Customer> findAll() {
		String sql = "SELECT id, name, email FROM customers";
        List<Customer> list = new ArrayList<>();
        try (Connection cn = TransactionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            throw new RuntimeException("Find all customers failed", e);
        }
        return list;
	}

	@Override
	public Optional<Customer> findById(int id) {
		String sql = "SELECT id, name, email FROM customers WHERE id=?";
		try {
			Connection cn = TransactionManager.getConnection();
			try (PreparedStatement ps = cn.prepareStatement(sql)) {
		            ps.setInt(1, id);
		            try (ResultSet rs = ps.executeQuery()) {
		                if (rs.next()) return Optional.of(map(rs));
		            }
		        } 
		}catch (Exception e) {
            throw new RuntimeException("Find customer failed", e);
        }
        return Optional.empty();
	}

	@Override
	public Customer insert(Customer c) {
		String sql = "INSERT INTO customers(name, email) VALUES(?, ?)";
		try {
			Connection cn = DB.getConnection();
			 try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		            ps.setString(1, c.getName());
		            ps.setString(2, c.getEmail());
		            ps.executeUpdate();
		         // Lấy ID tự sinh
		            try (ResultSet rs = ps.getGeneratedKeys()) {
		                if (rs.next()) c.setId(rs.getInt(1));
		            }
			 }       
            return c;
        }catch (SQLException e) {
        	 throw new RuntimeException("Insert customer failed", e);
        }
	}

	@Override
	public boolean update(Customer c) {
		String sql = "UPDATE customers SET name=?, email=? WHERE id=?";
		try {
			Connection cn = DB.getConnection();
			try (PreparedStatement ps = cn.prepareStatement(sql)) {
		            ps.setString(1, c.getName());
		            ps.setString(2, c.getEmail());
		            ps.setInt(3, c.getId());
		            return ps.executeUpdate() > 0;
			} 
		}
        catch (Exception e) {
            throw new RuntimeException("Update customer failed", e);
        }
	}

	@Override
	public boolean deleteById(int id) {
		String sql = "DELETE FROM customers WHERE id=?";
		try {
			Connection cn = DB.getConnection();
			try (
		             PreparedStatement ps = cn.prepareStatement(sql)) {
		            ps.setInt(1, id);
		            return ps.executeUpdate() > 0;
			}
		}
         catch (Exception e) {
            throw new RuntimeException("Delete customer failed", e);
        }
	}

}
