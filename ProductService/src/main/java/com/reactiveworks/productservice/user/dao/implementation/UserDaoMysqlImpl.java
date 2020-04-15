package com.reactiveworks.productservice.user.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.reactiveworks.productservice.dao.IUserDao;
import com.reactiveworks.productservice.db.DBUtil;
import com.reactiveworks.productservice.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productservice.db.exceptions.OperationNotSupportedException;
import com.reactiveworks.productservice.model.User;

/**
 *Mysql implementation of UserDao.
 */
public class UserDaoMysqlImpl implements IUserDao {

	private static final Logger LOGGER_OBJ = Logger.getLogger("UserDaoMysqlImpl.class");
	private static final String INSERT_QUERY = "INSERT INTO user VALUES(?,?,?,?,?);";
	private static final String UPDATE_QUERY = "UPDATE user SET phoneNumber=? WHERE userId=?;";
	private static final String DELETE_QUERY = "DELETE FROM user WHERE userId=?;";
	private static final String SELECT_QUERY = "SELECT * FROM user ;";

	/**
	 * Gets the records from the database.
	 * 
	 * @return the list of products into the database.
	 * @throws DataBaseAccessException        when unable to access the database.
	 * @throws DBOperationFailureException    when database operation fails.
	 * @throws InvalidDBRecordFormatException when the format of the database record
	 *                                        is invalid.
	 */
	@Override
	public List<User> getUsers() throws DataBaseAccessException, DBOperationFailureException {
		LOGGER_OBJ.debug("execution of getDBRecords() started");
		List<User> usersList = new ArrayList<User>();
		ResultSet res = null;
		User userObj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(SELECT_QUERY);
			res = statement.executeQuery(SELECT_QUERY);
			while (res.next()) {
				userObj = new User();
				userObj.setUserId(res.getString(1));
				userObj.setUserName(res.getString(2));
				userObj.setEmail(res.getString(3));
				userObj.setPhoneNumber(res.getString(4));
				userObj.setCity(res.getString(5));
				usersList.add(userObj);
			}

		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access  database");
			throw new DataBaseAccessException("unable to access user with id " + userObj.getUserId(), exp);
		} finally {

			DBUtil.cleanupdbresources(res, statement, connection);

		}
		LOGGER_OBJ.debug("execution of getDBRecords() completed");
		return usersList;
	}

	/**
	 * Inserts the record into the database.
	 * 
	 * @param product object of the product.
	 * @throws DBOperationFailureException    when operation on the database fails.
	 * @throws DataBaseAccessException        when unable to access the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void insertUser(User user) throws DataBaseAccessException, DBOperationFailureException {
		LOGGER_OBJ.debug("execution of insertRecord() started");
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getUserId());
			statement.setString(2, user.getUserName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPhoneNumber());
			statement.setString(5, user.getCity());
			statement.executeUpdate();
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access database");
			throw new DataBaseAccessException("unable to insert user with id "+ user.getUserId()+" into the database" ,exp);
		} finally {

			DBUtil.cleanupdbresources(resultSet, statement, connection);

		}
		LOGGER_OBJ.debug("execution of insertRecord() completed");
	}

	/**
	 * deletes the given record from the database.
	 * 
	 * @param product object of the product.
	 * @throws DBOperationFailureException    when operation on the database fails.
	 * @throws DataBaseAccessException        when unable to access the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void deleteUser(User user) throws DataBaseAccessException, DBOperationFailureException {
		LOGGER_OBJ.debug("execution of deleteRecord() started");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setString(1, user.getUserId());
			statement.executeUpdate();
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access stocktrade database");
			throw new DataBaseAccessException("unable to delete user with id " +user.getUserId(), exp);
		} finally {

			DBUtil.cleanupdbresources(null, statement, connection);

		}
		LOGGER_OBJ.debug("execution of deleteRecord() completed");
	}

	/**
	 * updates the record in the product database.
	 * 
	 * @param product product to be updated.
	 * @param price   updated value of the product price.
	 * @throws DBOperationFailureException    when operation on the database fails.
	 * @throws DataBaseAccessException        when unable to access the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void updateUser(User user, String phoneNumber)
			throws DataBaseAccessException, DBOperationFailureException {
		LOGGER_OBJ.debug("execution of updateRecord() started");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getdbconnection();
			statement = connection.prepareStatement(UPDATE_QUERY);
			statement.setString(1, phoneNumber);
			statement.setString(2, user.getUserId());
			statement.executeUpdate();
		} catch (SQLException exp) {
			LOGGER_OBJ.error("unable to access  database");
			throw new DataBaseAccessException("unable to update user with id "+user.getUserId() , exp);
		} finally {

			DBUtil.cleanupdbresources(null, statement, connection);

		}
		LOGGER_OBJ.debug("execution of updateRecord() completed");
	}

	/**
	 * inserts records into the database.
	 * 
	 * @param products list of products.
	 */
	public void insertRecords(List<User> users) {
		LOGGER_OBJ.debug("execution of getDBRecords() started");
		users.stream().forEach(user -> {
			try {
				insertUser(user);
			} catch (DataBaseAccessException | DBOperationFailureException e) {
				LOGGER_OBJ.error(e.getMessage());
				e.printStackTrace();
			}
		});
		LOGGER_OBJ.debug("execution of getDBRecords() completed");
	}
}