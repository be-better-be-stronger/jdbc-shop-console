package com.demo.jdbc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Integer id;
    private Customer customer;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private List<OrderItem> items;

    public Order() {}

    public Order(Integer id, Customer customer, LocalDateTime orderDate, BigDecimal total) {
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order #%d - %s - %,.0fđ".formatted(id, customer.getName(), total);
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

    
    // getters & setters
}
