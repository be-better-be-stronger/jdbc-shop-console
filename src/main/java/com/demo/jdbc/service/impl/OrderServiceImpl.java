package com.demo.jdbc.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import com.demo.jdbc.dao.CustomerDao;
import com.demo.jdbc.dao.OrderDao;
import com.demo.jdbc.dao.OrderItemDao;
import com.demo.jdbc.dao.ProductDao;
import com.demo.jdbc.dao.impl.CustomerDaoImpl;
import com.demo.jdbc.dao.impl.OrderDaoImpl;
import com.demo.jdbc.dao.impl.OrderItemDaoImpl;
import com.demo.jdbc.dao.impl.ProductDaoImpl;
import com.demo.jdbc.model.Customer;
import com.demo.jdbc.model.Order;
import com.demo.jdbc.model.OrderItem;
import com.demo.jdbc.model.Product;
import com.demo.jdbc.service.OrderService;
import com.demo.jdbc.util.TransactionManager;

public class OrderServiceImpl implements OrderService {
	private final CustomerDao customerDao = new CustomerDaoImpl();
	private final OrderDao orderDao = new OrderDaoImpl();
	private final OrderItemDao orderItemDao = new OrderItemDaoImpl();
	private final ProductDao productDao = new ProductDaoImpl();

	@Override
	public Order createOrder(int customerId, List<OrderItem> items) {
		try {
			TransactionManager.begin();
			Connection cn = TransactionManager.getConnection();

			Customer cus = customerDao.findById(customerId)
					.orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại"));

			// Tính tổng tiền & kiểm tra tồn kho
			BigDecimal total = BigDecimal.ZERO;
			for (OrderItem i : items) {
				Product p = productDao.findById(i.getProduct().getId())
						.orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));
				if (p.getQuantity() < i.getQuantity()) {
					cn.rollback();
					throw new IllegalStateException(String.format("Sản phẩm %s không đủ hàng", p.getName()));
				}
				i.setProduct(p);
				i.setPrice(p.getPrice());
				// Tính tổng tiền
				total = total.add(p.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
			}

			// Tạo order
			Order order = new Order();
			order.setCustomer(cus);
			order.setOrderDate(LocalDateTime.now());
			order.setTotal(total);
			order = orderDao.insert(cn, order);

			// Gán order_id cho item
			for (OrderItem i : items) {
				i.setOrder(order);
			}

			// Lưu items
			orderItemDao.insertItems(cn, items);

			// Cập nhật tồn kho sản phẩm
			for (OrderItem i : items) {
				Product p = i.getProduct();
				p.setQuantity(p.getQuantity() - i.getQuantity());
				productDao.update(cn, p);
			}

			TransactionManager.commit(); // ✅ Thành công

			order.setItems(items);

			System.out.println("Đã tạo đơn hàng #" + order.getId() + " - Tổng tiền: " + total + " - Ngày: "
					+ order.getOrderDate());
			return order;

		} catch (Exception e) {
			TransactionManager.rollback();
			handleError(e, "tạo đơn hàng");
		}
		return null;
	}

	@Override
	public List<Order> findAllWithDetails() {
		return orderDao.findAllWithDetails();
	}

	private  void handleError(Exception e, String action) {        
        System.err.println("Lỗi khi " + action + ": " + e.getMessage());
	}
}
