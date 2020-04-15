package com.reactiveworks.productservice.user.dao.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.reactiveworks.productservice.dao.IUserDao;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productservice.db.exceptions.OperationNotSupportedException;
import com.reactiveworks.productservice.model.User;

public class UserDaoCSVImpl implements IUserDao {
	private static final String FILE_NAME = "User.csv";
	private static final Logger LOGGER_OBJ = Logger.getLogger("UserDaoCSVImpl.class");

	/**
	 * Gets the records from the user database.
	 * 
	 * @return the list of users from the database.
	 * @throws DataBaseAccessException when unable to access the database.
	 */
	@Override
	public List<User> getUsers() throws DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getDBRecords() started");
		File file = new File(getClass().getClassLoader().getResource(FILE_NAME).getFile());
		List<User> users;

		try (Stream<String> line = Files.lines(Paths.get(file.toURI()))) {

			users = line.skip(1).map(str -> {

				return parseCSVLine(str);

			}).collect(Collectors.toList());

		} catch (IOException ioExp) {
			LOGGER_OBJ.error("unable to access the database "+ FILE_NAME);
			throw new DataBaseAccessException("unable to access the users database" , ioExp);

		}
		LOGGER_OBJ.debug("execution of getDBRecords() completed");
		return users;
	}

	/**
	 * converts one line in csv file to the user object.
	 * 
	 * @param str the string which has to be parsed.
	 * @return the object of the product.
	 * @throws InvalidDBRecordFormatException when the operation is not supported by
	 *                                        the database.
	 */
	private User parseCSVLine(String str) {
		LOGGER_OBJ.debug("execution of parseCSVLine() started");
		User userObj = new User();
		String[] userDetails = str.split(",");

		userObj.setUserId(userDetails[0]);
		userObj.setUserName(userDetails[1]);
		userObj.setEmail(userDetails[2]);
		userObj.setCity(userDetails[4]);
		LOGGER_OBJ.debug("execution of parseCSVLine() completed");
		return userObj;
	}

	/**
	 * inserts the record into the user database.
	 * 
	 * @param user user record that has to be inserted into the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void insertUser(User user) throws OperationNotSupportedException {
		throw new OperationNotSupportedException("insert Operation is not supported by user database");

	}

	/**
	 * deletes the given record from the user database.
	 * 
	 * @param user user to be deleted from the database.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void deleteUser(User user) throws OperationNotSupportedException {

		throw new OperationNotSupportedException("delete Operation is not supported by user database");
	}

	/**
	 * updates the record in the user database.
	 * 
	 * @param user        user to be updated.
	 * @param phoneNumber updated value of the user's phoneNumber.
	 * @throws OperationNotSupportedException when operation is not supported by the
	 *                                        database.
	 */
	@Override
	public void updateUser(User user, String phoneNumber) throws OperationNotSupportedException {

		throw new OperationNotSupportedException("update Operation is not supported by user database");
	}

}