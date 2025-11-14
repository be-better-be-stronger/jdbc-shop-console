package com.demo.jdbc.controller;

import java.util.List;
import java.util.Scanner;

import com.demo.jdbc.model.Report;
import com.demo.jdbc.service.ReportService;
import com.demo.jdbc.service.impl.ReportServiceImpl;

public class ReportController {
	private ReportService reportService = new ReportServiceImpl();
	private Scanner sc = new Scanner(System.in);
	public void showMenu() {
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
