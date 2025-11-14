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
import com.demo.jdbc.util.TransactionManager;

public class ProductServiceImpl implements ProductService {
	private final ProductDao productDao = new ProductDaoImpl();
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    /** Lấy toàn bộ sản phẩm */
    @Override
    public List<Product> findAll() {
        try {
            return productDao.findAll();
        } catch (Exception e) {
        	e.printStackTrace();
            handleError(e, "tải danh sách sản phẩm");
            return List.of();
        }
    }

    /** Lấy sản phẩm theo danh mục */
    @Override
    public List<Product> findByCategoryId(int categoryId) {
        try {
        	categoryDao.findById(categoryId).orElseThrow(() ->  new IllegalArgumentException("Danh mục không tồn tại!")); 
            List<Product> products = productDao.findByCategoryId(categoryId);
            System.out.println(
            	    products.isEmpty()
            	        ? "Danh mục này hiện chưa có sản phẩm nào."
            	        : "Tìm thấy " + products.size() + " sản phẩm trong danh mục #" + categoryId
            );
            return products;
        } catch (Exception e) {
            handleError(e, "tìm sản phẩm theo danh mục");
            return List.of();
        }
    }

    /** Tìm sản phẩm theo ID */
    @Override
    public Optional<Product> findById(int id) {
        try {
            return productDao.findById(id);
        } catch (Exception e) {
            handleError(e, String.format("tìm sản phẩm có id=%d", id));
            return Optional.empty();
        }
    }

    @Override
    public Product create(String name, BigDecimal price, int qty, int categoryId) {
    	validateInput(name, price, qty, categoryId);
        try {
            TransactionManager.begin();
            Category c = categoryDao.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));
            Product p = new Product(null, name.trim(), price, qty, c);
            Product created = productDao.insert(p);
            TransactionManager.commit();
            System.out.println("✅ Tạo sản phẩm thành công!");
            return created;
        }catch (Exception e) {
        	TransactionManager.rollback();
        	handleError(e, "thêm sản phẩm mới");
        	return null; 
		}
		       
    }

    @Override
    public boolean update(int id, String name, BigDecimal price, int qty, int categoryId) {
    	validateInput(name, price, qty, categoryId);
        try {
            TransactionManager.begin();
            checkId(id);
            Category c = categoryDao.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));

            Product p = new Product(id, name.trim(), price, qty, c);
            Connection cn = TransactionManager.getConnection();

            boolean updated = productDao.update(cn, p);
            TransactionManager.commit();
            return updated;
        } catch (IllegalArgumentException e) {
            TransactionManager.rollback();
            System.err.println("Lỗi dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            TransactionManager.rollback();
            handleError(e, "cập nhật sản phẩm");
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
    	checkId(id);
    	if (productDao.existsInOrderItems(id)) {
            System.out.println("Không thể xóa sản phẩm vì đã được sử dụng trong đơn hàng!");
            return false;
        }
        return productDao.deleteById(id);
    }
    
    private void checkId(int id) {
    	productDao.findById(id).orElseThrow(
    			() -> new IllegalArgumentException(String.format("Không tìm thấy sản phẩm có ID = %d", id)));    	
    }
    
    /** Xử lý lỗi chi tiết trong service */
    private  void handleError(Exception e, String action) {        
            System.err.println("Lỗi khi " + action + ": " + e.getMessage());
    }
    
    private void validateInput(String name, BigDecimal price, int quantity, int categoryId) {
    	if (name == null || name.isBlank())
            throw new IllegalArgumentException("Tên sản phẩm không được trống");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Giá phải lớn hơn 0");
        if(quantity <= 0)
        	throw new IllegalArgumentException("Nhập số lượng lớn hơn 0");
        if(categoryId <= 0)
        	throw new IllegalArgumentException("Id danh mục không hợp lệ");
    }

}
