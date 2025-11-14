package com.demo.jdbc.exception;

/**
 * Custom runtime exception thrown when a specific entity
 * (e.g., Product, Category, Customer, etc.) cannot be found in the database.
 *
 * <p>This exception is used across the Service and DAO layers
 * to signal that a requested record does not exist.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * throw new EntityNotFoundException("Product not found with id = " + id);
 * }</pre>
 *
 * @author Thanh
 * @since 1.0
 */
public class EntityNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new EntityNotFoundException with the specified message.
     *
     * @param message the detail message explaining which entity was not found.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new EntityNotFoundException with the specified message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
