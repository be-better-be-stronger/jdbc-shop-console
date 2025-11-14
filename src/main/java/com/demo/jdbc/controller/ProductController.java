package com.demo.jdbc.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.demo.jdbc.exception.EntityNotFoundException;
import com.demo.jdbc.model.Product;
import com.demo.jdbc.service.CategoryService;
import com.demo.jdbc.service.ProductService;
import com.demo.jdbc.service.impl.CategoryServiceImpl;
import com.demo.jdbc.service.impl.ProductServiceImpl;

/**
 * Console-based controller for managing {@link Product} entities.
 * <p>
 * This controller handles user interaction for product management, including
 * listing, creating, updating, and deleting products. It delegates all business
 * logic to the {@link ProductService}.
 * </p>
 *
 * <p>
 * Part of the 3-layer architecture: Controller → Service → DAO
 * </p>
 *
 * @author Thanh
 * @since 1.0
 */
public class ProductController {
	private ProductService productService = new ProductServiceImpl();
	private CategoryService categoryService = new CategoryServiceImpl();
	private Scanner sc = new Scanner(System.in);

	public void showMenu() {
		while (true) {
			System.out.println("\n=== QUẢN LÝ SẢN PHẨM ===");
			System.out.println("1. Danh sách sản phẩm");
			System.out.println("2. Thêm sản phẩm");
			System.out.println("3. Cập nhật sản phẩm");
			System.out.println("4. Xóa sản phẩm");
			System.out.println("5. Tìm theo danh mục");
			System.out.println("0. Quay lại menu chính");
			System.out.print("Chọn: ");

			String choice = sc.nextLine().trim();
			switch (choice) {
				case "1" -> listAll();
				case "2" -> create();
				case "3" -> update();
				case "4" -> delete();
				case "5" -> findByCategory();
				case "0" -> {
					System.out.println("⬅️ Quay lại menu chính...");
					return;
				}
				default -> System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng nhập lại!");
			}
		}
	}

	/**
	 * Lists all available products.
	 */
	private void listAll() {
		List<Product> list = productService.findAll();
		if (list.isEmpty()) {
			System.out.println("Hiện chưa có sản phẩm nào.");
			return;
		}
		System.out.println("\nDANH SÁCH SẢN PHẨM:");
		list.forEach(System.out::println);
	}

	/**
	 * Creates a new product based on user input.
	 */
	private void create() {
		try {
			System.out.print("Tên sản phẩm: ");
			String name = sc.nextLine().trim();

			System.out.print("Giá sản phẩm (VD: 190000): ");
			BigDecimal price = new BigDecimal(sc.nextLine().trim());

			System.out.print("Số lượng: ");
			int qty = Integer.parseInt(sc.nextLine().trim());

			System.out.print("ID danh mục: ");
			int categoryId = Integer.parseInt(sc.nextLine().trim());

			Product p = productService.create(name, price, qty, categoryId);
			System.out.println("✅ Đã thêm sản phẩm thành công: " + p.getName());

		} catch (IllegalArgumentException e) {
			System.out.println("⚠️ Lỗi dữ liệu nhập: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("❌ Lỗi không xác định: " + e.getMessage());
		}
	}

	/**
	 * Updates an existing product.
	 */
	private void update() {
		try {
			System.out.print("Nhập ID sản phẩm cần cập nhật: ");
			int id = Integer.parseInt(sc.nextLine().trim());

			Optional<Product> found = productService.findById(id);
			if (found.isEmpty()) {
				System.out.println("⚠️ Không tìm thấy sản phẩm ID = " + id);
				return;
			}

			System.out.print("Tên mới: ");
			String name = sc.nextLine().trim();

			System.out.print("Giá mới: ");
			BigDecimal price = new BigDecimal(sc.nextLine().trim());

			System.out.print("Số lượng mới: ");
			int qty = Integer.parseInt(sc.nextLine().trim());

			System.out.print("ID danh mục mới: ");
			int categoryId = Integer.parseInt(sc.nextLine().trim());

			boolean updated = productService.update(id, name, price, qty, categoryId);
			if (updated)
				System.out.println("✅ Cập nhật thành công sản phẩm #" + id);
			else
				System.out.println("⚠️ Không thể cập nhật sản phẩm #" + id);

		} catch (NumberFormatException e) {
			System.out.println("⚠️ Giá trị nhập không hợp lệ (phải là số).");
		} catch (Exception e) {
			System.out.println("❌ Lỗi cập nhật sản phẩm: " + e.getMessage());
		}
	}

	/**
	 * Deletes a product by ID.
	 */
	private void delete() {
		try {
			System.out.print("Nhập ID sản phẩm cần xóa: ");
			int id = Integer.parseInt(sc.nextLine().trim());

			boolean deleted = productService.delete(id);
			if (deleted)
				System.out.println("🗑️ Đã xóa sản phẩm #" + id);
			else
				System.out.println("⚠️ Không tìm thấy sản phẩm #" + id);

		} catch (NumberFormatException e) {
			System.out.println("⚠️ ID phải là số nguyên hợp lệ!");
		} catch (Exception e) {
			System.out.println("❌ Lỗi khi xóa sản phẩm: " + e.getMessage());
		}
	}

	/**
	 * Finds and lists products by category ID.
	 */
	private void findByCategory() {
		try {
			System.out.print("Nhập ID danh mục: ");
			int categoryId = Integer.parseInt(sc.nextLine().trim());
			
			categoryService.findById(categoryId).orElseThrow(() -> 
					new EntityNotFoundException(String.format("Danh mục có id=%d không tồn tại", categoryId)));
			
			List<Product> products = productService.findByCategoryId(categoryId);
			if (products.isEmpty()) {
				System.out.println("Không có sản phẩm nào thuộc danh mục #" + categoryId);
			} else {
				System.out.println("\nSẢN PHẨM TRONG DANH MỤC #" + categoryId + ":");
				products.forEach(System.out::println);
			}

		} catch (NumberFormatException e) {
			System.out.println("ID danh mục phải là số!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
