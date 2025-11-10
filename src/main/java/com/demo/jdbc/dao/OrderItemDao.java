package com.demo.jdbc.dao;

import java.sql.Connection;
import java.util.List;

import com.demo.jdbc.model.OrderItem;

public interface OrderItemDao {
	/**
	 * Persists a batch of {@link OrderItem} entities into the database using the provided connection.
	 *
	 * <p>This method is designed for batch insertion of multiple order items that belong
	 * to a specific {@link Order}. It uses the given {@link Connection} to execute
	 * all insert operations within the same transactional context. The connection is
	 * <strong>not</strong> closed or committed by this method — transaction boundaries
	 * (commit/rollback) are managed by the caller.</p>
	 *
	 * <p>Each {@link OrderItem} in the list is expected to have its associated
	 * {@link Order} and {@link Product} properly initialized with valid identifiers.</p>
	 *
	 * @param cn an active and open {@link Connection} to the database; must not be {@code null}.
	 * @param items a non-empty {@link List} of {@link OrderItem} instances to be inserted; 
	 *              must not be {@code null}.
	 * @throws RuntimeException if a database access error occurs or any insert fails.
	 * @see java.sql.PreparedStatement#addBatch()
	 * @see java.sql.Connection#commit()
	 * @see java.sql.Connection#rollback()
	 */
	void insertItems(Connection cn, List<OrderItem> items);
}
