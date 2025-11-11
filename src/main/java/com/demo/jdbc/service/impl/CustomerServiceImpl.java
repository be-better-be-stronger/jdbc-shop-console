package com.demo.jdbc.service.impl;

import java.util.List;
import java.util.Optional;

import com.demo.jdbc.dao.CustomerDao;
import com.demo.jdbc.dao.impl.CustomerDaoImpl;
import com.demo.jdbc.model.Customer;
import com.demo.jdbc.service.CustomerService;
import com.demo.jdbc.util.TransactionManager;

public class CustomerServiceImpl implements CustomerService {
	private final CustomerDao dao = new CustomerDaoImpl();

    @Override
    public List<Customer> findAll() {
    	try {
            return dao.findAll();
        } catch (Exception e) {
            handleError(e, "tải danh sách khách hàng");
            return List.of(); // trả list rỗng để UI không lỗi
        }
    }

    @Override
    public Optional<Customer> findById(int id) {
    	try {
            return dao.findById(id);
        } catch (Exception e) {
            handleError(e, String.format("tìm khác hàng có id=%d", id));
            return Optional.empty();
        }
    }

    @Override
    public Customer create(String name, String email) {
    	if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new IllegalArgumentException("Tên và email không được trống");
        }
        try {
            TransactionManager.begin();
            Customer c = new Customer(null, name.trim(), email.trim());
            Customer inserted = dao.insert(c);
            TransactionManager.commit();
            System.out.println("Thêm khách hàng thành công!");
            return inserted;

        } catch (Exception e) {
            TransactionManager.rollback();
            handleError(e, "thêm khách hàng");         
            return null;
        }
        
    }


    @Override
    public boolean update(int id, String name, String email) {
    	if (name == null || name.isBlank() || email == null || email.isBlank()) {
            System.err.println("⚠️ Tên và email không được trống");
            return false;
        }
        try {
            TransactionManager.begin();
            checkId(id);
            boolean updated = dao.update(new Customer(id, name.trim(), email.trim()));
            TransactionManager.commit();
            return updated;
        } catch (Exception e) {
            TransactionManager.rollback();
            handleError(e, "cập nhật khách hàng");
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
            System.out.println("✅ Xóa khách hàng thành công!");
            return deleted;

        } catch (Exception e) {
            TransactionManager.rollback();
            handleError(e, "xóa khách hàng");
            return false;
        }
    }
    
    private void checkId(int id) {
    	dao.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy khách hàng có ID = %d", id)));
    }
    
    /** Xử lý lỗi chi tiết trong service */
    private void handleError(Exception e, String action) {
        Throwable cause = e.getCause();
        if (cause instanceof java.sql.SQLException sqlEx && sqlEx.getErrorCode() == 1062) {
            System.err.println("Email đã tồn tại, vui lòng dùng email khác!");
        } else {
            System.err.println("Lỗi khi " + action + ": " + e.getMessage());
        }
    }
}
