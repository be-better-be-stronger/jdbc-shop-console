package com.demo.jdbc.controller;

import java.util.Scanner;

import com.demo.jdbc.model.Customer;
import com.demo.jdbc.service.CustomerService;
import com.demo.jdbc.service.impl.CustomerServiceImpl;

public class CustomerController {
	private CustomerService cusService = new CustomerServiceImpl();
	private Scanner sc = new Scanner(System.in);
	public void showMenu() {
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
                    Customer newCustomer = cusService.create(name, email);
                    if(cusService != null) System.out.println("Đã thêm: " + newCustomer);
                }
                case "3" -> {
                    System.out.print("ID cần sửa: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Tên mới: ");
                    String name = sc.nextLine();
                    System.out.print("Email mới: ");
                    String email = sc.nextLine();
                    System.out.println(cusService.update(id, name, email) ? "Cập nhật thành công!" : "Cập nhật thất bại!");
                }
                case "4" -> {
                    System.out.print("ID cần xóa: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.println(cusService.delete(id) ? "Đã xóa!" : "Thất bại!");
                }
                case "0" -> { return; }
                default -> System.out.println("Sai lựa chọn!");
            }
        }
    }

}
