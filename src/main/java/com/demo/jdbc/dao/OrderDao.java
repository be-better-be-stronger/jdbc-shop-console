package com.demo.jdbc.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import com.demo.jdbc.model.Order;

/**
 * Data Access Object (DAO) interface for performing CRUD operations 
 * on {@link Order} entities in the database.
 *
 * <p>This interface defines the main methods for interacting with
 * the {@code orders} table, including creating new orders and 
 * retrieving existing ones with or without their associated details.</p>
 *
 * <p>Implementations of this interface should handle SQL operations,
 * resource management (connections, statements, result sets),
 * and object mapping between relational data and the {@link Order} model.</p>
 *
 * @author [Your Name]
 * @since 1.0
 */
public interface OrderDao {
	
	/**
     * Inserts a new {@link Order} record into the database.
     *
     * @param order the {@link Order} object to be persisted.
     * @return the persisted {@link Order} object with its generated ID 
     *         (if applicable).
     * @throws RuntimeException if the insert operation fails.
     */
	Order insert(Connection cn, Order order);
	
	/**
     * Finds an {@link Order} by its unique identifier.
     *
     * @param id the ID of the order to look up.
     * @return an {@link Optional} containing the {@link Order} if found,
     *         or an empty {@link Optional} if no order exists with the given ID.
     */
    Optional<Order> findById(int id);
    
    /**
     * Retrieves all {@link Order} records from the database, 
     * including their related details (e.g., customer, order items).
     *
     * @return a {@link List} of {@link Order} objects with full details populated.
     *         The list may be empty if no orders are found.
     */
    List<Order> findAllWithDetails();
}
