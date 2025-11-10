package com.demo.jdbc.service;

import java.util.List;
import java.util.Optional;

import com.demo.jdbc.model.Customer;

/**
 * Service interface for managing {@link Customer} entities.
 * <p>
 * This interface defines business operations for customer management,
 * including creation, update, retrieval, and deletion.
 * Implementations typically interact with a DAO or repository layer to perform persistence operations.
 * </p>
 *
 * <p><b>Usage example:</b></p>
 * <pre>{@code
 * CustomerService service = new CustomerServiceImpl();
 * Customer newCustomer = service.create("John Doe", "john@example.com");
 * }</pre>
 *
 * @author Thanh
 * @since 1.0
 */
public interface CustomerService {
	
	/**
     * Retrieves all customers available in the system.
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
	     * Creates and saves a new customer with the given name and email.
	     *
	     * @param name  the name of the new customer; must not be {@code null} or blank.
	     * @param email the email address of the new customer; must not be {@code null} or blank.
	     * @return the newly created {@link Customer} entity.
	     * @throws DuplicateEmailException if a customer with the same email already exists.
	     */
	    Customer create(String name, String email);
	    
	    /**
	     * Updates the details of an existing customer.
	     *
	     * @param id    the ID of the customer to update.
	     * @param name  the new name to assign; must not be {@code null} or blank.
	     * @param email the new email to assign; must not be {@code null} or blank.
	     * @return {@code true} if the update was successful; {@code false} if the customer was not found.
	     */
	    boolean update(int id, String name, String email);
	    
	    /**
	     * Deletes a customer by its unique identifier.
	     *
	     * @param id the ID of the customer to delete.
	     * @return {@code true} if the customer was successfully deleted; {@code false} if not found.
	     */
	    boolean delete(int id);
}
