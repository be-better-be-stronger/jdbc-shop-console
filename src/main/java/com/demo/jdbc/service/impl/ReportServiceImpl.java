package com.demo.jdbc.service.impl;

import java.util.List;

import com.demo.jdbc.dao.ReportDao;
import com.demo.jdbc.dao.impl.ReportDaoImpl;
import com.demo.jdbc.model.Report;
import com.demo.jdbc.service.ReportService;

public class ReportServiceImpl implements ReportService {
    private final ReportDao dao = new ReportDaoImpl();

    @Override
    public List<Report> revenueByProduct() {
        return dao.revenueByProduct();
    }

    @Override
    public List<Report> revenueByCustomer() {
        return dao.revenueByCustomer();
    }
}