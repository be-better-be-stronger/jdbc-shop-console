package com.demo.jdbc.service.impl;

import java.util.List;

import com.demo.jdbc.dao.ReportDao;
import com.demo.jdbc.dao.impl.ReportDaoImpl;
import com.demo.jdbc.model.Report;
import com.demo.jdbc.service.ReportService;
import com.demo.jdbc.util.TransactionManager;

public class ReportServiceImpl implements ReportService {
    private final ReportDao dao = new ReportDaoImpl();

    @Override
    public List<Report> revenueByProduct() {
    	try {
            TransactionManager.begin();
            List<Report> list = dao.revenueByProduct();
            TransactionManager.commit();
            return list;
        } catch (Exception e) {
            TransactionManager.rollback();
            handleError(e, "report by product");
        }
    	return List.of();
    }

    @Override
    public List<Report> revenueByCustomer() {
    	try {
            TransactionManager.begin();
            List<Report> list = dao.revenueByCustomer();
            TransactionManager.commit();
            return list;
        } catch (Exception e) {
            TransactionManager.rollback();
            handleError(e, "report by customer");
        }
		return List.of();
    }
    
    private  void handleError(Exception e, String action) {        
        System.err.println("Lỗi khi " + action + ": " + e.getMessage());
    }
}