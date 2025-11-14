package com.demo.jdbc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.demo.jdbc.exception.EntityNotFoundException;
import com.demo.jdbc.model.Order;
import com.demo.jdbc.model.OrderItem;
import com.demo.jdbc.model.Product;
import com.demo.jdbc.service.OrderService;
import com.demo.jdbc.service.ProductService;
import com.demo.jdbc.service.impl.OrderServiceImpl;
import com.demo.jdbc.service.impl.ProductServiceImpl;

/**
 * Console-based controller for managing {@link Order} entities.
 * <p>
 * This controller handles user input and output related to order management,
 * including creating new orders and listing existing orders with their details.
 * </p>
 *
 * <p>Part of the 3-layer architecture: Controller → Service → DAO</p>
 *
 * @author Thanh
 * @since 1.0
 */
public class OrderController {
	private final OrderService orderService = new OrderServiceImpl();
	private final ProductService productService = new ProductServiceImpl();
    private final Scanner sc = new Scanner(System.in);

    /**
     * Displays the order management menu and handles user choices.
     */    
    public  void showMenu() {
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

    private  void createOrderConsole() {
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

                Product p = new Product();
                p.setId(pid);
                // Tạo OrderItem (giá sẽ lấy trong service khi xử lý)
                OrderItem item = new OrderItem();
                item.setProduct(p);
                item.setQuantity(qty);
                items.add(item);
            }

            if (items.isEmpty()) {
                System.out.println("Chưa chọn sản phẩm nào, đơn hàng bị hủy!");
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
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    
    private  void showAllOrders() {
        List<Order> list = orderService.findAllWithDetails();
        if (list.isEmpty()) {
            System.out.println("⚠️ Chưa có đơn hàng nào!");
            return;
        }
        for (Order o : list) {
            System.out.println("-------------------------------------------------");
            
            System.out.println(String.format("Đơn hàng #%d - %s (%s)", 
            		o.getId(), o.getCustomer().getName(), o.getOrderDate()));
            for (OrderItem i : o.getItems()) {
                System.out.println(i.getProduct().getName() +
                        " x" + i.getQuantity() +
                        " (" + i.getPrice() + "đ)");
            }
            System.out.println(String.format("Tổng: %s", o.getTotal()));
        }
        System.out.println("-------------------------------------------------");
    }
}
