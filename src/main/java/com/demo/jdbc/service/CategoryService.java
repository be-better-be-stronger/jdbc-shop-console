package com.demo.jdbc.service;

import java.util.List;
import java.util.Optional;

import com.demo.jdbc.model.Category;

/**
 * Service interface for managing {@link Category} entities.
 * <p>
 * This interface defines business operations for handling product categories,
 * including creation, update, retrieval, and deletion.
 * Implementations may interact with DAO or repository layers to perform data persistence.
 * </p>
 *
 * <p><b>Usage example:</b></p>
 * <pre>{@code
 * CategoryService service = new CategoryServiceImpl();
 * List<Category> list = service.findAll();
 * }</pre>
 *
 * @author Thanh
 * @since 1.0
 */
public interface CategoryService {
	
	/**
     * Retrieves all categories available in the system.
     *
     * @return a list of all {@link Category} entities; never {@code null},
     *         but may be empty if no categories exist.
     */
	List<Category> findAll();
	
	 /**
     * Finds a category by its unique identifier.
     *
     * @param id the ID of the category to find.
     * @return an {@link Optional} containing the found {@link Category} if present;
     *         otherwise, an empty {@link Optional}.
     */
    Optional<Category> findById(int id);
    
    /**
     * Creates and saves a new category with the given name.
     *
     * @param name the name of the new category; must not be {@code null} or blank.
     * @return the newly created {@link Category} entity.
     */
    Category create(String name);
    
    /**
     * Updates the name of an existing category.
     *
     * @param id the ID of the category to update.
     * @param newName the new name to assign; must not be {@code null} or blank.
     * @return {@code true} if the update was successful; {@code false} if the category was not found.
     */
    boolean update(int id, String newName);
    
    /**
     * Deletes a category by its unique identifier.
     *
     * @param id the ID of the category to delete.
     * @return {@code true} if the category was successfully deleted; {@code false} if not found.
     */
    boolean delete(int id);
}
