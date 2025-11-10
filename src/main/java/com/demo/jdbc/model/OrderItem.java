package com.demo.jdbc.model;

import java.math.BigDecimal;

public class OrderItem {
    private Integer id;
    private Order order;
    private Product product;
    private Integer quantity;
    private BigDecimal price;

    public OrderItem() {}
    public OrderItem(Integer id, Product product, Integer quantity, BigDecimal price) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "%s x%d = %,.0fđ".formatted(product.getName(), quantity, price.multiply(BigDecimal.valueOf(quantity)));
    }
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    // getters & setters
}
