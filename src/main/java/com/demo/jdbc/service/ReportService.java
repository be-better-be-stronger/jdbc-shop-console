package com.demo.jdbc.service;

import java.util.List;

import com.demo.jdbc.model.Report;

public interface ReportService {
	List<Report> revenueByProduct();
    List<Report> revenueByCustomer();
}
