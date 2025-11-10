package com.demo.jdbc.dao;

import java.util.List;
import java.util.Optional;

import com.demo.jdbc.model.Category;
/**
 * {@code CategoryDao} định nghĩa các phương thức thao tác với bảng {@code Category} trong cơ sở dữ liệu.
 * <p>
 * Interface này cung cấp các chức năng CRUD cơ bản: 
 * - Lấy danh sách tất cả category
 * - Tìm category theo ID
 * - Thêm mới, cập nhật, hoặc xóa category
 * </p>
 *
 * <p><b>Chú ý:</b> Các phương thức trong DAO chỉ khai báo hành vi; 
 * phần hiện thực (implementation) sẽ được viết trong lớp cụ thể, ví dụ {@code CategoryDaoImpl}.</p>
 *
 * @author Bạn
 * @version 1.0
 * @since Java 21
 */
public interface CategoryDao {
	
	/**
     * Lấy danh sách tất cả các category trong cơ sở dữ liệu.
     *
     * @return danh sách các {@link Category} (List không bao giờ null, có thể rỗng nếu không có dữ liệu).
     */
	List<Category> findAll();
	
	/**
     * Tìm category theo mã định danh (ID).
     *
     * @param id mã định danh của category cần tìm.
     * @return một {@link Optional} chứa {@link Category} nếu tìm thấy, 
     *         hoặc {@link Optional#empty()} nếu không tồn tại.
     */
    Optional<Category> findById(int id);
    
    /**
     * Thêm mới một category vào cơ sở dữ liệu.
     *
     * @param c đối tượng {@link Category} cần thêm mới.
     * @return {@link Category} sau khi đã được thêm thành công (có thể bao gồm ID do DB sinh ra).
     */
    Category insert(Category c);
    
    /**
     * Cập nhật thông tin category đã tồn tại.
     *
     * @param c đối tượng {@link Category} chứa thông tin đã chỉnh sửa.
     * @return {@code true} nếu cập nhật thành công, {@code false} nếu không tìm thấy hoặc lỗi.
     */
    boolean update(Category c);
    
    /**
     * Xóa category theo mã định danh (ID).
     *
     * @param id mã định danh của category cần xóa.
     * @return {@code true} nếu xóa thành công, {@code false} nếu không tìm thấy hoặc lỗi.
     */
    boolean deleteById(int id);
}
