package com.reactiveworks.productservice.product.dao.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.reactiveworks.productservice.dao.IProductDao;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productservice.db.exceptions.OperationNotSupportedException;
import com.reactiveworks.productservice.model.Product;

public class ProductDaoCSVImpl implements IProductDao {

	private static final String FILE_NAME = "Product.csv";
	private static final Logger LOGGER_OBJ = Logger.getLogger("ProductDaoCSVImpl.class");

	/**
	 * Gets the records from the product database.
	 * 
	 * @return the list of products into the database.
	 * @throws DataBaseAccessException when unable to access the database.
	 */
	public List<Product> getProducts() throws DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getDBRecords() started");
		File file = new File(getClass().getClassLoader().getResource(FILE_NAME).getFile());

		List<Product> products;

		try (Stream<String> line = Files.lines(Paths.get(file.toURI()))) {

			products = line.skip(1).map(str -> {
				try {
					return parseCSVLine(str);
				} catch (InvalidDBRecordFormatException e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
		} catch (IOException ioExp) {
			LOGGER_OBJ.error("unable to access the product with id= ");
			throw new DataBaseAccessException("unable to access the product database" , ioExp);

		}
		LOGGER_OBJ.debug("execution of getDBRecords() completed");
		return products;
	}

	/**
	 * converts one line in csv file to the product object.
	 * 
	 * @param str the string which has to be parsed.
	 * @return the object of the product.
	 * @throws InvalidDBRecordFormatException when the operation is not supported by
	 *                                        the database.
	 */
	private Product parseCSVLine(String str) throws InvalidDBRecordFormatException {
		LOGGER_OBJ.debug("execution of parseLine() started");
		Product productObj = new Product();
		String[] prodDetails = str.split(",");

		productObj.setProductId(prodDetails[0]);
		productObj.setProductName(prodDetails[1]);
		productObj.setProductCategory(prodDetails[2]);
		productObj.setAvailableCity(prodDetails[4]);

		try {
			int price = Integer.parseInt(prodDetails[3]);
			productObj.setPrice(price);
		} catch (NumberFormatException numFormatExp) {
			LOGGER_OBJ.error("format of the database record price is invalid in "+str);
			throw new InvalidDBRecordFormatException("format of the database record price is invalid" + numFormatExp);
		}
		LOGGER_OBJ.debug("execution of parseLine() completed");
		return productObj;
	}

	/**
	 * Inserts the record into the product database.
	 * 
	 * @param product object of the product.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void insertProduct(Product product) throws OperationNotSupportedException {

		throw new OperationNotSupportedException("insert Operation is not supported by product database");

	}

	/**
	 * deletes the given record from the product database.
	 * 
	 * @param product object of the product.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void deleteProduct(Product product) throws OperationNotSupportedException {
		throw new OperationNotSupportedException("delete Operation is not supported by product database");

	}

	/**
	 * updates the record in the product database.
	 * 
	 * @param product product to be updated.
	 * @param price   updated value of the product price.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void updateProduct(Product product, int price) throws OperationNotSupportedException {
		throw new OperationNotSupportedException("update Operation is not supported by product database");

	}

}