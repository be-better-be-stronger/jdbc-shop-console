package com.demo.jdbc.controller;

import java.util.Scanner;

public class AppController {
	private final Scanner sc = new Scanner(System.in);
    private final ProductController productController = new ProductController();
    private final CustomerController customerController = new CustomerController();
    private final OrderController orderController = new OrderController();
    private final CategoryController categoryController = new CategoryController();
    private final ReportController reportController = new ReportController();

    public void start() {
        while (true) {
            System.out.println("\n=== HỆ THỐNG QUẢN LÝ CỬA HÀNG ===");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Quản lý đơn hàng");
            System.out.println("4. Quản lý danh mục");
            System.out.println("5. Báo cáo thống kê");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> productController.showMenu();
                case 2 -> customerController.showMenu();
                case 3 -> orderController.showMenu();
                case 4 -> categoryController.showMenu();
                case 5 -> reportController.showMenu();
                case 0 -> {
                    System.out.println("Tạm biệt!");
                    return;
                }
                default -> System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }
}
