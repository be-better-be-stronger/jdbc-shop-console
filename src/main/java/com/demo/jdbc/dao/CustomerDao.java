package com.demo.jdbc.dao;

import java.util.List;
import java.util.Optional;

import com.demo.jdbc.model.Customer;
/**
 * Data Access Object (DAO) interface for performing CRUD operations on {@link Customer} entities.
 * <p>
 * This interface defines the contract for interacting with the customer data source.
 * Implementations may use JDBC, Hibernate, JPA, or any persistence framework.
 * </p>
 *
 * <p><b>Typical usage:</b></p>
 * <pre>{@code
 * CustomerDao dao = new CustomerDaoImpl();
 * List<Customer> customers = dao.findAll();
 * }</pre>
 *
 * @author Thanh
 * @since 1.0
 */
public interface CustomerDao {
	/**
     * Retrieves all customers from the data source.
     *
     * @return a list of all {@link Customer} entities; never {@code null}, 
     *         but may be empty if no customers exist.
     */
	List<Customer> findAll();
	
	/**
     * Finds a customer by its unique identifier.
     *
     * @param id the ID of the customer to find.
     * @return an {@link Optional} containing the found {@link Customer} if present;
     *         otherwise, an empty {@link Optional}.
     */
    Optional<Customer> findById(int id);
    
    /**
     * Inserts a new customer into the data source.
     *
     * @param c the {@link Customer} entity to insert; must not be {@code null}.
     * @return the inserted {@link Customer} object with updated fields (e.g., generated ID).
     */
    Customer insert(Customer c);
    
    /**
     * Updates the details of an existing customer.
     *
     * @param c the {@link Customer} entity with updated values; must not be {@code null}.
     * @return {@code true} if the update was successful; {@code false} otherwise.
     */
    boolean update(Customer c);
    
    /**
     * Deletes a customer from the data source by its unique identifier.
     *
     * @param id the ID of the customer to delete.
     * @return {@code true} if the customer was successfully deleted; {@code false} if not found.
     */
    boolean deleteById(int id);
}
