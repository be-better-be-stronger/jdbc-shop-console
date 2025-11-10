package com.demo.jdbc.dao;

import java.util.List;

import com.demo.jdbc.model.Report;
/**
 * Data Access Object (DAO) interface for generating revenue reports
 * from persisted business data such as orders, products, and customers.
 *
 * <p>This interface defines query operations that return aggregated
 * {@link Report} objects, representing business insights such as total
 * revenue per product or per customer. Implementations are responsible
 * for executing SQL aggregation queries (e.g., {@code SUM}, {@code GROUP BY})
 * and mapping the results into {@link Report} domain objects.</p>
 *
 * <p>Each method operates in a read-only context and should not modify
 * the underlying data.</p>
 *
 * @author [Thanh]
 * @since 1.0
 */
public interface ReportDao {
	/**
     * Retrieves aggregated revenue data grouped by product.
     *
     * <p>The resulting list contains one {@link Report} entry per product,
     * typically including product name, total sales amount, and total quantity sold.
     * The query is expected to perform SQL aggregation using
     * {@code GROUP BY product_id} or equivalent logic.</p>
     *
     * @return a non-null {@link List} of {@link Report} objects representing
     *         total revenue per product. The list may be empty if no data exists.
     * @throws RuntimeException if a database access or query execution error occurs.
     */
	List<Report> revenueByProduct();
	/**
     * Retrieves aggregated revenue data grouped by customer.
     *
     * <p>The resulting list contains one {@link Report} entry per customer,
     * typically including customer name, total spending amount, and number of orders.
     * The query is expected to perform SQL aggregation using
     * {@code GROUP BY customer_id} or equivalent logic.</p>
     *
     * @return a non-null {@link List} of {@link Report} objects representing
     *         total revenue per customer. The list may be empty if no data exists.
     * @throws RuntimeException if a database access or query execution error occurs.
     */
    List<Report> revenueByCustomer();
}
