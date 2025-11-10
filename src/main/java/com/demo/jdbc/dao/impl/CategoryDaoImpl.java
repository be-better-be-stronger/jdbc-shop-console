package com.demo.jdbc.dao.impl;


import java.sql.*;
import java.util.*;

import com.demo.jdbc.dao.CategoryDao;
import com.demo.jdbc.model.Category;
import com.demo.jdbc.util.DB;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> findAll() {
        String sql = "SELECT id, name FROM categories ORDER BY id";
        List<Category> list = new ArrayList<>();
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
        } catch (Exception e) {
            throw new RuntimeException("Find all categories failed", e);
        }
        return list;
    }

    @Override
    public Optional<Category> findById(int id) {
        String sql = "SELECT id, name FROM categories WHERE id=?";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            throw new RuntimeException("Find category failed", e);
        }
        return Optional.empty();
    }

    @Override
    public Category insert(Category c) {
        String sql = "INSERT INTO categories(name) VALUES(?)";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
            return c;
        } catch (Exception e) {
            throw new RuntimeException("Insert category failed", e);
        }
    }

    @Override
    public boolean update(Category c) {
        String sql = "UPDATE categories SET name=? WHERE id=?";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setInt(2, c.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Update category failed", e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM categories WHERE id=?";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Delete category failed", e);
        }
    }
}
