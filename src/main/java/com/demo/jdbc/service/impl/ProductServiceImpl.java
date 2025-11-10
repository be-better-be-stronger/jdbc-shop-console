package com.demo.jdbc.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import com.demo.jdbc.dao.CategoryDao;
import com.demo.jdbc.dao.ProductDao;
import com.demo.jdbc.dao.impl.CategoryDaoImpl;
import com.demo.jdbc.dao.impl.ProductDaoImpl;
import com.demo.jdbc.model.Category;
import com.demo.jdbc.model.Product;
import com.demo.jdbc.service.ProductService;
import com.demo.jdbc.util.DB;

public class ProductServiceImpl implements ProductService {
	private final ProductDao productDao = new ProductDaoImpl();
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        return productDao.findByCategoryId(categoryId);
    }

    @Override
    public Optional<Product> findById(int id) {
        return productDao.findById(id);
    }

    @Override
    public Product create(String name, BigDecimal price, int qty, int categoryId) {
        if (price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Giá phải > 0");
        Category c = categoryDao.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));
        Product p = new Product(null, name.trim(), price, qty, c);
        return productDao.insert(p);
    }

    @Override
    public boolean update(int id, String name, BigDecimal price, int qty, int categoryId) {
        try(Connection cn = DB.getConnection()) {
        	cn.setAutoCommit(false); // bật transaction để kiểm soát commit/rollback
            Category c = categoryDao.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));
            Product p = new Product(id, name.trim(), price, qty, c);
            boolean success = productDao.update(cn, p);
            cn.commit(); // nếu update thành công, commit transaction
            return success;
		} catch (IllegalArgumentException e) {
	        // lỗi validate dữ liệu đầu vào (ví dụ: category không tồn tại)
	        throw e; // ném lại để controller hiển thị thông báo phù hợp
	    } catch (Exception e) {
	        // lỗi truy cập cơ sở dữ liệu hoặc runtime khác
	        try {
	            // rollback nếu transaction thất bại
	            DB.getConnection().rollback();
	        } catch (Exception ignore) {}
	        throw new RuntimeException("Cập nhật sản phẩm thất bại", e);
	    }
    }

    @Override
    public boolean delete(int id) {
    	if (productDao.existsInOrderItems(id)) {
            System.out.println("⚠️ Không thể xóa sản phẩm vì đã được sử dụng trong đơn hàng!");
            return false;
        }
        return productDao.deleteById(id);
    }
}
