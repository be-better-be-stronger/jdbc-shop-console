package com.demo.jdbc.controller;

import java.util.Scanner;

import com.demo.jdbc.model.Category;
import com.demo.jdbc.service.CategoryService;
import com.demo.jdbc.service.impl.CategoryServiceImpl;

public class CategoryController {
	private static final Scanner sc = new Scanner(System.in);
	private static final CategoryService catService = new CategoryServiceImpl();
	 public void showMenu() {
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
	                        Category newCategory = catService.create(name);
	                        if(newCategory != null) System.out.println("Đã thêm: " +  newCategory);				
	                }
	                case "3" -> {
	                    System.out.print("ID cần sửa: ");
	                    int id = Integer.parseInt(sc.nextLine());
	                    System.out.print("Tên mới: ");
	                    String name = sc.nextLine();
	                    System.out.println(catService.update(id, name) ? "Sửa thành công!" : "Sửa thất bại!");
	                }
	                case "4" -> {
	                    System.out.print("ID cần xóa: ");
	                    int id = Integer.parseInt(sc.nextLine());
	                    System.out.println(catService.delete(id) ? "Đã xóa!" : "Thất bại!");
	                }
	                case "0" -> { return; }
	                default -> System.out.println("Sai lựa chọn!");
	            }
	        }
	    }
}
