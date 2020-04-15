package com.reactiveworks.productservice.user.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.reactiveworks.productservice.dao.IUserDao;
import com.reactiveworks.productservice.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productservice.product.dao.ProductDaoFactory;
import com.reactiveworks.productservice.user.dao.implementation.UserDaoCSVImpl;
import com.reactiveworks.productservice.user.dao.implementation.UserDaoMysqlImpl;


/**
 * Factory class for UserDao.
 */
public class UserDaoFactory {
	private static final String PROPERTY_FILE = "dbtype.properties";
	private static final Logger LOGGER_OBJ = Logger.getLogger("UserDaoFactory.class");
	private static final String DB_TYPE = "dbtype";
	private static final String CSV = "csv";
	private static final String MYSQL = "mysql";

	public static Properties properties = null;

	/**
	 * Creates the object of UserDao implementation class.
	 * 
	 * @return the UserDao implementation class object.
	 * @throws DBOerationFailureException when operation on the database fails.
	 */
	public static IUserDao getInstance() throws DBOperationFailureException {
		LOGGER_OBJ.debug("execution of getInstance() started");
		IUserDao userDaoObj = null;
		if (properties == null) {
			try (InputStream input = ProductDaoFactory.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {

				properties = new Properties();
				properties.load(input);

			} catch (IOException exp) {
				LOGGER_OBJ.error("not able to read the properties file " + PROPERTY_FILE);
				throw new DBOperationFailureException("not able to read the file " + PROPERTY_FILE, exp);
			}
		}

		if (((String) properties.get(DB_TYPE)).equalsIgnoreCase(CSV)) {
			userDaoObj = new UserDaoCSVImpl();
		} else if (((String) properties.get(DB_TYPE)).equalsIgnoreCase(MYSQL)) {
			userDaoObj = new UserDaoMysqlImpl();
		} else {
			LOGGER_OBJ.debug(properties.get(DB_TYPE) + " implementation does not exist.");
			userDaoObj = new UserDaoCSVImpl(); // default stockTrade Dao Object
		}
		LOGGER_OBJ.debug("execution of getInstance() completed");
		return userDaoObj;
	}

	/**
	 * Creates the object of UserDao implementation class.
	 * 
	 * @return the UserDao implementation class object.
	 * @throws DBOerationFailureException when operation on the database fails.
	 */
	public static IUserDao getInstance(String dbType) throws DBOperationFailureException {
		LOGGER_OBJ.debug("execution of getInstance() started");
		IUserDao userDaoObj = null;

		if(dbType!=null) {
			if (dbType.equalsIgnoreCase(CSV)) {
				userDaoObj = new UserDaoCSVImpl();
			} else if (dbType.equalsIgnoreCase(MYSQL)) {
				userDaoObj = new UserDaoMysqlImpl();
			} else {
				LOGGER_OBJ.debug(dbType + " implementation does not exist.");
				userDaoObj = new UserDaoCSVImpl(); // default stockTrade Dao Object
			}
		}
		
		LOGGER_OBJ.debug("execution of getInstance() completed");
		return userDaoObj;
	}
}
