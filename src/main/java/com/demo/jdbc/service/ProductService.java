package com.demo.jdbc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.demo.jdbc.model.Product;

/**
 * Service interface for managing {@link Product} entities.
 * <p>
 * This interface defines the business operations related to product management,
 * including creation, update, retrieval, and deletion of products.
 * Implementations typically interact with the DAO or repository layer
 * to perform persistence and business logic operations.
 * </p>
 *
 * <p><b>Usage example:</b></p>
 * <pre>{@code
 * ProductService service = new ProductServiceImpl();
 * Product p = service.create("Nai fillet", new BigDecimal("190000"), 10, 1);
 * }</pre>
 *
 * @author Thanh
 * @since 1.0
 */
public interface ProductService {
	
	/**
     * Retrieves all products available in the system.
     *
     * @return a list of all {@link Product} entities; never {@code null},
     *         but may be empty if no products exist.
     */
	List<Product> findAll();
	
	 /**
     * Retrieves all products that belong to a specific category.
     *
     * @param categoryId the ID of the category whose products should be retrieved.
     * @return a list of {@link Product} entities belonging to the given category;
     *         never {@code null}, but may be empty if no products match.
     */
    List<Product> findByCategoryId(int categoryId);
    
    /**
     * Finds a product by its unique identifier.
     *
     * @param id the ID of the product to find.
     * @return an {@link Optional} containing the found {@link Product} if present;
     *         otherwise, an empty {@link Optional}.
     */
    Optional<Product> findById(int id);
    
    /**
     * Creates and saves a new product with the given information.
     *
     * @param name        the name of the product; must not be {@code null} or blank.
     * @param price       the unit price of the product; must not be {@code null} or negative.
     * @param qty         the available quantity of the product; must be non-negative.
     * @param categoryId  the ID of the category to which this product belongs.
     * @return the newly created {@link Product} entity.
     * @throws IllegalArgumentException if the input values are invalid (e.g., negative price or quantity).
     * @throws EntityNotFoundException if the specified category does not exist.
     */
    Product create(String name, BigDecimal price, int qty, int categoryId);
    
    /**
     * Updates the details of an existing product.
     *
     * @param id          the ID of the product to update.
     * @param name        the new product name; must not be {@code null} or blank.
     * @param price       the updated price; must not be {@code null} or negative.
     * @param qty         the updated quantity; must be non-negative.
     * @param categoryId  the new category ID to associate with the product.
     * @return {@code true} if the update was successful; {@code false} if the product was not found.
     */
    boolean update(int id, String name, BigDecimal price, int qty, int categoryId);
    
    /**
     * Deletes a product by its unique identifier.
     *
     * @param id the ID of the product to delete.
     * @return {@code true} if the product was successfully deleted; {@code false} if not found.
     */
    boolean delete(int id);
 
}
