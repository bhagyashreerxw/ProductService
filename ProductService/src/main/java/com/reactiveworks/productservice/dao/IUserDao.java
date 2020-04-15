package com.reactiveworks.productservice.dao;

import java.util.List;

import com.reactiveworks.productservice.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productservice.db.exceptions.OperationNotSupportedException;
import com.reactiveworks.productservice.model.User;

public interface IUserDao {

	
	default public void insertRecords(List<User> users) {}

	/**
	 * Gets the records from the user database.
	 * 
	 * @return the list of users from the database.
	 * @throws DataBaseAccessException when unable to access the database.
	 */
	public List<User> getUsers() throws DataBaseAccessException, DataBaseAccessException, DBOperationFailureException, InvalidDBRecordFormatException;

	/**
	 * inserts the record into the user database.
	 * 
	 * @param user user record that has to be inserted into the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void insertUser(User user) throws OperationNotSupportedException, DataBaseAccessException, DBOperationFailureException;

	/**
	 * deletes the given record from the user database.
	 * 
	 * @param user user to be deleted from the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void deleteUser(User user) throws OperationNotSupportedException, DataBaseAccessException, DBOperationFailureException;

	/**
	 * updates the record in the user database.
	 * 
	 * @param user user to be updated.
	 * @param phoneNumber   updated value of the user's phoneNumber.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	public void updateUser(User user, String phoneNumber) throws OperationNotSupportedException, DataBaseAccessException, DBOperationFailureException;

	
}
