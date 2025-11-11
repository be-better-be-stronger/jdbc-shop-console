package com.demo.jdbc.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.demo.jdbc.dao.CategoryDao;
import com.demo.jdbc.dao.impl.CategoryDaoImpl;
import com.demo.jdbc.model.Category;
import com.demo.jdbc.service.CategoryService;
import com.demo.jdbc.util.TransactionManager;

public class CategoryServiceImpl implements CategoryService {
	private final CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
    	try {
            return dao.findAll();
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tải danh sách danh mục: " + e.getMessage());
            return List.of(); // trả list rỗng để UI không lỗi
        }
    }

    @Override
    public Optional<Category> findById(int id) {
    	try {
            return dao.findById(id);
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tìm danh mục ID " + id + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Category create(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Tên danh mục không được trống");
        try {
            TransactionManager.begin(); // bắt đầu transaction

            Category c = new Category(null, name.trim());
            dao.insert(c);

            TransactionManager.commit(); // commit transaction
            System.out.println("Tạo danh mục thành công!");
            return c;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối CSDL: " + e.getMessage());
            return null;
        } catch (RuntimeException e) {
            TransactionManager.rollback();
            handleError(e, "thêm danh mục mới");
            return null;
        } 
    }

    @Override
    public boolean update(int id, String newName) {
    	if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Tên mới không được trống");
        }
        try {
            TransactionManager.begin();
            checkId(id);
            boolean updated = dao.update(new Category(id, newName.trim()));
            TransactionManager.commit();
            System.out.println("Cập nhật danh mục thành công!");
            return updated;
        } catch (Exception e) {
            TransactionManager.rollback();
            handleError(e, "cập nhật danh mục");
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
    	try {
            TransactionManager.begin();
            checkId(id);
            boolean deleted = dao.deleteById(id);
            TransactionManager.commit();
            return deleted;
        } catch (Exception e) {
            TransactionManager.rollback();
            handleError(e, "xóa danh mục");
            return false;
        }
    }
    
    private void checkId(int id) {
    	dao.findById(id).orElseThrow(
    			() -> new IllegalArgumentException(String.format("ID=%d không tồn tại!", id))
    	);
    }
    
    /** Xử lý lỗi chi tiết trong service */
    private void handleError(Exception e, String action) {
        Throwable cause = e.getCause();
        if (cause instanceof java.sql.SQLException sqlEx && sqlEx.getErrorCode() == 1062) {
            System.err.println("Tên danh mục đã tồn tại, vui lòng dùng tên khác!");
        } else {
            System.err.println("Lỗi khi " + action + ": " + e.getMessage());
        }
    }
}
