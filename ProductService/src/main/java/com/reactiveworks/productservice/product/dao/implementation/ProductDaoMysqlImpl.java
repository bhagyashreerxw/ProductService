package com.reactiveworks.productservice.product.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.reactiveworks.productservice.dao.IProductDao;
import com.reactiveworks.productservice.db.DBUtil;
import com.reactiveworks.productservice.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productservice.db.exceptions.OperationNotSupportedException;
import com.reactiveworks.productservice.model.Product;

/**
 * Mysql implementation of ProductDao.
 */
public class ProductDaoMysqlImpl implements IProductDao{
	
	private static final Logger LOGGER_OBJ=Logger.getLogger("ProductDaoMysqlImpl.class");
	private static final String INSERT_QUERY="INSERT INTO product VALUES(?,?,?,?,?);";
	private static final String UPDATE_QUERY="UPDATE product SET price=? WHERE productId=?;";
	private static final String DELETE_QUERY="DELETE FROM product WHERE productId=?;";
	private static final String SELECT_QUERY="SELECT * FROM product ;";
	
	
	/**
	 * Gets the records from the product database.
	 * 
	 * @return the list of products into the database.
	 * @throws DataBaseAccessException when unable to access the database.
	 * @throws DBOperationFailureException when database operation fails.
	 * @throws InvalidDBRecordFormatException  when the format of the database record is invalid.
	 */
	@Override
	public List<Product> getProducts() throws DataBaseAccessException, DBOperationFailureException, InvalidDBRecordFormatException {
		LOGGER_OBJ.debug("execution of getProducts() started");
		List<Product> productsList=new ArrayList<Product>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement statement = null;
		Product productObj = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(SELECT_QUERY);
			res = statement.executeQuery(SELECT_QUERY);
			while (res.next()) {
				productObj=new Product();
				productObj.setProductId(res.getString(1));
				productObj.setProductName(res.getString(2));
				productObj.setProductCategory(res.getString(3));
				int price=Integer.parseInt(res.getString(4));
				productObj.setPrice(price);
				productObj.setAvailableCity(res.getString(5));
				productsList.add(productObj);
			}
			
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access  product with id " + productObj.getProductId());
			throw new DataBaseAccessException("unable to access product database" + exp);
		} catch (NumberFormatException numFormatExp) {
			LOGGER_OBJ.error("format of product db record is invalid");
			throw new InvalidDBRecordFormatException("format of product with id "+productObj.getProductId()+" is invalid" , numFormatExp);
		}finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}
		LOGGER_OBJ.debug("execution of getProducts() completed");
		return productsList;
	}

	/**
	 * Inserts the record into the product database.
	 * 
	 * @param product object of the product.
	 * @throws DBOperationFailureException when operation on the database fails.
	 * @throws DataBaseAccessException  when unable to access the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void insertProduct(Product product) throws DataBaseAccessException, DBOperationFailureException   {
		LOGGER_OBJ.debug("execution of insertProduct() started");
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, product.getProductId());
			statement.setString(2, product.getProductName());
			statement.setString(3, product.getProductCategory());
			statement.setInt(4, product.getPrice());
			statement.setString(5, product.getAvailableCity());
			statement.executeUpdate();
		}catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access database");
			throw new DataBaseAccessException("unable to access product database" , exp);
		}  finally {

			DBUtil.cleanupdbresources(resultSet, statement, connection);

		}
		LOGGER_OBJ.debug("execution of insertProduct() completed");
	}

	/**
	 * deletes the given record from the product database.
	 * 
	 * @param product object of the product.
	* @throws DBOperationFailureException when operation on the database fails.
	 * @throws DataBaseAccessException  when unable to access the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void deleteProduct(Product product) throws DataBaseAccessException, DBOperationFailureException {
		LOGGER_OBJ.debug("execution of deleteProduct() started");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setString(1, product.getProductId());
			statement.executeUpdate();
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access the database");
			throw new DataBaseAccessException("unable to delete product with id " +product.getProductId(), exp);
		} finally {

			DBUtil.cleanupdbresources(null, statement, connection);

		}
		LOGGER_OBJ.debug("execution of deleteProduct() completed");
	}

	/**
	 * updates the record in the product database.
	 * 
	 * @param product product to be updated.
	 * @param price   updated value of the product price.
	 * @throws DBOperationFailureException when operation on the database fails.
	 * @throws DataBaseAccessException  when unable to access the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */

	@Override
	public void updateProduct(Product product, int price) throws DataBaseAccessException, DBOperationFailureException  {
		LOGGER_OBJ.debug("execution of updateProduct() started");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(UPDATE_QUERY);
			statement.setInt(1, price);
			
			statement.setString(2, product.getProductId());
			statement.executeUpdate();
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access  database");
			throw new DataBaseAccessException("unable to update product with id "+product.getProductId() , exp);
		} finally {

			DBUtil.cleanupdbresources(null, statement, connection);

		}
		LOGGER_OBJ.debug("execution of updateProduct() completed");
	}
	
	/**
	 * inserts records into the database.
	 * @param products list of products.
	 */
	public void insertRecords(List<Product> products)  {
		LOGGER_OBJ.debug("execution of insertRecords() started");
		products.stream().forEach(product->{
			try {
				insertProduct(product);
			} catch (DataBaseAccessException | DBOperationFailureException e) {
				
				e.printStackTrace();
			}
		});
		LOGGER_OBJ.debug("execution of insertRecords() completed");
	}

}
