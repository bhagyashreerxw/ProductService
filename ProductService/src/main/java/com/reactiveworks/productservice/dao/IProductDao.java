package com.reactiveworks.productservice.dao;

import java.util.List;

import com.reactiveworks.productservice.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productservice.db.exceptions.OperationNotSupportedException;
import com.reactiveworks.productservice.model.Product;

public interface IProductDao {
	
	default public void insertRecords(List<Product> products) {}

	/**
	 * Gets the records from the product database.
	 * 
	 * @return the list of products into the database.
	 * @throws DataBaseAccessException when unable to access the database.
	 */
	public List<Product> getProducts() throws DataBaseAccessException, DBOperationFailureException,InvalidDBRecordFormatException;

	/**
	 * Inserts the record into the product database.
	 * 
	 * @param product object of the product.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void insertProduct(Product product) throws OperationNotSupportedException, DataBaseAccessException, DBOperationFailureException ;

	/**
	 * Deletes the given record from the product database.
	 * 
	 * @param product product to be deleted from the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void deleteProduct(Product product) throws OperationNotSupportedException, DataBaseAccessException, DBOperationFailureException;

	/**
	 * Updates the record in the product database.
	 * 
	 * @param product product to be updated.
	 * @param price   updated value of the product price.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void updateProduct(Product product, int price) throws OperationNotSupportedException, DataBaseAccessException, DBOperationFailureException;

}