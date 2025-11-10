package com.demo.jdbc.service.impl;

import java.util.List;
import java.util.Optional;

import com.demo.jdbc.dao.CustomerDao;
import com.demo.jdbc.dao.impl.CustomerDaoImpl;
import com.demo.jdbc.model.Customer;
import com.demo.jdbc.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	private final CustomerDao dao = new CustomerDaoImpl();

    @Override
    public List<Customer> findAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Customer> findById(int id) {
        return dao.findById(id);
    }

    @Override
    public Customer create(String name, String email) {
        if (name.isBlank() || email.isBlank())
            throw new IllegalArgumentException("Tên và email không được trống");
        return dao.insert(new Customer(null, name.trim(), email.trim()));
    }

    @Override
    public boolean update(int id, String name, String email) {
        return dao.update(new Customer(id, name, email));
    }

    @Override
    public boolean delete(int id) {
        return dao.deleteById(id);
    }
}
