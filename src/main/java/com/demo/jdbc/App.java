package com.demo.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.demo.jdbc.model.Order;
import com.demo.jdbc.model.OrderItem;
import com.demo.jdbc.model.Product;
import com.demo.jdbc.model.Report;
import com.demo.jdbc.service.CategoryService;
import com.demo.jdbc.service.CustomerService;
import com.demo.jdbc.service.OrderService;
import com.demo.jdbc.service.ProductService;
import com.demo.jdbc.service.ReportService;
import com.demo.jdbc.service.impl.CategoryServiceImpl;
import com.demo.jdbc.service.impl.CustomerServiceImpl;
import com.demo.jdbc.service.impl.OrderServiceImpl;
import com.demo.jdbc.service.impl.ProductServiceImpl;
import com.demo.jdbc.service.impl.ReportServiceImpl;

public class App {
	private static final Scanner sc = new Scanner(System.in);
    private static final CategoryService catService = new CategoryServiceImpl();
    private static final CustomerService cusService = new CustomerServiceImpl();
    private static final ProductService proService = new ProductServiceImpl();
    private static final OrderService orderService = new OrderServiceImpl();
    private static final ReportService reportService = new ReportServiceImpl();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Quản lý danh mục");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Quản lý sản phẩm");
            System.out.println("4. Xem danh sách đơn hàng");
            System.out.println("5. Báo cáo thống kê");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            switch (sc.nextLine().trim()) {
                case "1" -> menuCategory();
                case "2" -> menuCustomer();
                case "3" -> menuProduct();
                case "4" -> menuOrder();
                case "5" -> menuReport();
                case "0" -> { System.out.println("Tạm biệt!"); return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ============ CATEGORY ============
    private static void menuCategory() {
        while (true) {
            System.out.println("\n-- Quản lý danh mục --");
            System.out.println("1. Xem tất cả");
            System.out.println("2. Thêm mới");
            System.out.println("3. Sửa");
            System.out.println("4. Xóa");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> catService.findAll().forEach(System.out::println);
                case "2" -> {
                    System.out.print("Tên danh mục: ");
                    String name = sc.nextLine();
                    System.out.println("Đã thêm: " + catService.create(name));
                }
                case "3" -> {
                    System.out.print("ID cần sửa: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Tên mới: ");
                    String name = sc.nextLine();
                    System.out.println(catService.update(id, name) ? "Sửa thành công!" : "Không tìm thấy ID!");
                }
                case "4" -> {
                    System.out.print("ID cần xóa: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.println(catService.delete(id) ? "Đã xóa!" : "Không tìm thấy ID!");
                }
                case "0" -> { return; }
                default -> System.out.println("Sai lựa chọn!");
            }
        }
    }

    // ============ CUSTOMER ============
    private static void menuCustomer() {
        while (true) {
            System.out.println("\n-- Quản lý khách hàng --");
            System.out.println("1. Xem tất cả");
            System.out.println("2. Thêm mới");
            System.out.println("3. Sửa");
            System.out.println("4. Xóa");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> cusService.findAll().forEach(System.out::println);
                case "2" -> {
                    System.out.print("Tên: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.println("Đã thêm: " + cusService.create(name, email));
                }
                case "3" -> {
                    System.out.print("ID cần sửa: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Tên mới: ");
                    String name = sc.nextLine();
                    System.out.print("Email mới: ");
                    String email = sc.nextLine();
                    System.out.println(cusService.update(id, name, email) ? "Sửa thành công!" : "Không tìm thấy ID!");
                }
                case "4" -> {
                    System.out.print("ID cần xóa: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.println(cusService.delete(id) ? "Đã xóa!" : "Không tìm thấy ID!");
                }
                case "0" -> { return; }
                default -> System.out.println("Sai lựa chọn!");
            }
        }
    }

    // ============ PRODUCT ============
    private static void menuProduct() {
        while (true) {
            System.out.println("\n-- Quản lý sản phẩm --");
            System.out.println("1. Xem tất cả");
            System.out.println("2. Thêm mới");
            System.out.println("3. Sửa");
            System.out.println("4. Xóa");
            System.out.println("5. Xem theo danh mục");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> proService.findAll().forEach(System.out::println);
                case "2" -> {
                    System.out.print("Tên: ");
                    String name = sc.nextLine();
                    System.out.print("Giá: ");
                    BigDecimal price = new BigDecimal(sc.nextLine());
                    System.out.print("Số lượng: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    System.out.print("ID danh mục: ");
                    int catId = Integer.parseInt(sc.nextLine());
                    System.out.println("Đã thêm: " + proService.create(name, price, qty, catId));
                }
                case "3" -> {
                    System.out.print("ID sản phẩm: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Tên mới: ");
                    String name = sc.nextLine();
                    System.out.print("Giá mới: ");
                    BigDecimal price = new BigDecimal(sc.nextLine());
                    System.out.print("Số lượng mới: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    System.out.print("ID danh mục mới: ");
                    int catId = Integer.parseInt(sc.nextLine());
                    System.out.println(proService.update(id, name, price, qty, catId) ? "Cập nhật thành công!" : "Không tìm thấy ID!");
                }
                case "4" -> {
                    System.out.print("ID cần xóa: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.println(proService.delete(id) ? "Đã xóa!" : "Không tìm thấy ID!");
                }
                case "5" -> {
                    System.out.print("Nhập ID danh mục: ");
                    int catId = Integer.parseInt(sc.nextLine());
                    proService.findByCategoryId(catId).forEach(System.out::println);
                }
                case "0" -> { return; }
                default -> System.out.println("Sai lựa chọn!");
            }
        }
    }
    
    private static void menuOrder() {
        while (true) {
            System.out.println("\n-- Quản lý đơn hàng --");
            System.out.println("1. Tạo đơn hàng");
            System.out.println("2. Xem danh sách chi tiết");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> createOrderConsole();
                case "2" -> showAllOrders();
                case "0" -> { return; }
                default -> System.out.println("Sai lựa chọn!");
            }
        }
    }
    
    private static void createOrderConsole() {
        try {
            System.out.println("\n=== TẠO ĐƠN HÀNG MỚI ===");
            // 1️⃣ Nhập khách hàng
            System.out.print("Nhập ID khách hàng: ");
            int cusId = Integer.parseInt(sc.nextLine());

            // 2️⃣ Nhập các sản phẩm mua
            List<OrderItem> items = new ArrayList<>();
            while (true) {
                System.out.print("Nhập ID sản phẩm (0 để kết thúc): ");
                int pid = Integer.parseInt(sc.nextLine());
                if (pid == 0) break;

                System.out.print("Số lượng: ");
                int qty = Integer.parseInt(sc.nextLine());

                // Tạo đối tượng sản phẩm chỉ có id (service sẽ load đầy đủ)
                Product p = new Product();
                p.setId(pid);

                // Tạo OrderItem (giá sẽ lấy trong service khi xử lý)
                OrderItem item = new OrderItem();
                item.setProduct(p);
                item.setQuantity(qty);
                items.add(item);
            }

            if (items.isEmpty()) {
                System.out.println("⚠️ Chưa chọn sản phẩm nào, đơn hàng bị hủy!");
                return;
            }

            // 3️⃣ Gọi service tạo đơn hàng
            Order order = orderService.createOrder(cusId, items);

            // 4️⃣ Hiển thị kết quả
            System.out.println("\n✅ Đã tạo đơn hàng #" + order.getId());
            System.out.println("Khách hàng: " + order.getCustomer().getName());
            System.out.println("Ngày: " + order.getOrderDate());
            System.out.println("Chi tiết:");
            for (OrderItem i : order.getItems()) {
                System.out.println("  • " + i.getProduct().getName() + " x" + i.getQuantity() +
                        " = " + i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())) + "đ");
            }
            System.out.println("Tổng cộng: " + order.getTotal() + "đ");

        } catch (Exception e) {
            System.err.println("Lỗi khi tạo đơn hàng: " + e.getMessage());
        }
    }

    
    private static void showAllOrders() {
        List<Order> list = orderService.findAllWithDetails();
        if (list.isEmpty()) {
            System.out.println("⚠️ Chưa có đơn hàng nào!");
            return;
        }
        for (Order o : list) {
            System.out.println("-------------------------------------------------");
            System.out.println("Đơn hàng #" + o.getId() +
                    " - " + o.getCustomer().getName() +
                    " (" + o.getOrderDate() + ")" +
                    " - Tổng: " + o.getTotal() + "đ");
            for (OrderItem i : o.getItems()) {
                System.out.println("  • " + i.getProduct().getName() +
                        " x" + i.getQuantity() +
                        " (" + i.getPrice() + "đ)");
            }
        }
        System.out.println("-------------------------------------------------");
    }
    
    private static void menuReport() {
        while (true) {
            System.out.println("\n-- BÁO CÁO THỐNG KÊ --");
            System.out.println("1. Doanh thu theo sản phẩm");
            System.out.println("2. Doanh thu theo khách hàng");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> showReport(reportService.revenueByProduct(), "DOANH THU THEO SẢN PHẨM");
                case "2" -> showReport(reportService.revenueByCustomer(), "DOANH THU THEO KHÁCH HÀNG");
                case "0" -> { return; }
                default -> System.out.println("Sai lựa chọn!");
            }
        }
    }
    private static void showReport(List<Report> list, String title) {
        System.out.println("\n===== " + title + " =====");
        System.out.printf("%-25s %10s %15s%n", "Tên", "Số lượng", "Doanh thu");
        System.out.println("--------------------------------------------------------------");
        for (Report r : list) {
            System.out.printf("%-25s %10d %15.2f%n", r.getName(), r.getQuantity(), r.getRevenue());
        }
        System.out.println("--------------------------------------------------------------");
    }


}
