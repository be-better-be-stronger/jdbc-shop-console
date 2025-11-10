package com.demo.jdbc.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;



/**
 * Data Access Object (DAO) interface for performing CRUD operations on {@link Product} entities.
 * <p>
 * This interface defines the contract for accessing and manipulating product data
 * in the underlying data source (e.g., database).
 * Implementations may use JDBC, JPA, Hibernate, or other persistence frameworks.
 * </p>
 *
 * <p><b>Usage example:</b></p>
 * <pre>{@code
 * ProductDao dao = new ProductDaoImpl();
 * List<Product> products = dao.findAll();
 * }</pre>
 *
 * @author Thanh
 * @since 1.0
 */
import com.demo.jdbc.model.Product;

public interface ProductDao {
	/**
     * Retrieves all products from the data source.
     *
     * @return a list of all {@link Product} entities; never {@code null},
     *         but may be empty if no products are found.
     */
	List<Product> findAll();
	
	/**
     * Finds a product by its unique identifier.
     *
     * @param id the ID of the product to find.
     * @return an {@link Optional} containing the found {@link Product} if present;
     *         otherwise, an empty {@link Optional}.
     */
    Optional<Product> findById(int id);
    

    /**
     * Inserts a new product into the data source.
     *
     * @param p the {@link Product} entity to insert; must not be {@code null}.
     * @return the inserted {@link Product} entity with updated fields (e.g., generated ID).
     */
    Product insert(Product p);
    
    /**
     * Updates an existing {@link Product} record in the database using the given connection.
     *
     * <p>This method assumes that the provided {@link Connection} is valid and open.
     * It does not close the connection; transaction handling (commit/rollback) is the caller's responsibility.</p>
     *
     * @param cn an active {@link Connection} to the database; must not be {@code null}.
     * @param p  the {@link Product} entity containing the updated field values; must not be {@code null}.
     * @return {@code true} if the update affected at least one record; {@code false} otherwise.
     * @throws RuntimeException if a database access error occurs.
     */
    boolean update(Connection cn, Product p);
    
    /**
     * Deletes a product from the data source by its unique identifier.
     *
     * @param id the ID of the product to delete.
     * @return {@code true} if the product was successfully deleted; {@code false} if not found.
     */
    boolean deleteById(int id);
    
    /**
     * Retrieves all products belonging to a specific category.
     *
     * @param categoryId the ID of the category.
     * @return a list of {@link Product} entities associated with the given category ID;
     *         never {@code null}, but may be empty if no products match.
     */
    List<Product> findByCategoryId(int categoryId);
    
    boolean existsInOrderItems(int productId);

}
