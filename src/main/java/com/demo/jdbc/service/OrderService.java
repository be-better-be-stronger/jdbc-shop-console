package com.demo.jdbc.service;

import java.util.List;

import com.demo.jdbc.model.Order;
import com.demo.jdbc.model.OrderItem;

public interface OrderService {
	Order createOrder(int customerId, List<OrderItem> items);
	List<Order> findAllWithDetails();

}
