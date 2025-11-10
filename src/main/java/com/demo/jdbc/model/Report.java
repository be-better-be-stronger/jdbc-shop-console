package com.demo.jdbc.model;

import java.math.BigDecimal;

public class Report {
    private String name;
    private long quantity;
    private BigDecimal revenue;

    public Report(String name, long quantity, BigDecimal revenue) {
        this.name = name;
        this.quantity = quantity;
        this.revenue = revenue;
    }

    // getters + setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }

    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }

    @Override
    public String toString() {
        return String.format("%-25s %10d %15.2f", name, quantity, revenue);
    }
}
