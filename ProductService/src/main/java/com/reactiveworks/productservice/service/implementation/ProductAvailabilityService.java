package com.reactiveworks.productservice.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.reactiveworks.productservice.dao.IProductDao;
import com.reactiveworks.productservice.dao.IUserDao;
import com.reactiveworks.productservice.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.db.exceptions.InvalidDBRecordFormatException;
import com.reactiveworks.productservice.model.Product;
import com.reactiveworks.productservice.model.User;
import com.reactiveworks.productservice.product.dao.ProductDaoFactory;
import com.reactiveworks.productservice.service.IProductAvailabilityService;
import com.reactiveworks.productservice.user.dao.UserDaoFactory;

/**
 * Provides product services to the user.
 */
public class ProductAvailabilityService implements IProductAvailabilityService {
	private static final Logger LOGGER_OBJ = Logger.getLogger("ProductAvailabilityService.class");

	private List<Product> productList;
	private List<User> userList;

	public ProductAvailabilityService() {
		LOGGER_OBJ.debug("execution of ProductAvailabilityService() started");
		IProductDao productDao;
		IUserDao userDao;
		try {
			productDao = ProductDaoFactory.getInstance();
			userDao = UserDaoFactory.getInstance();
			productList = productDao.getProducts();
			userList = userDao.getUsers();
		} catch (DBOperationFailureException operationFailureExp) {
			LOGGER_OBJ.error("unable to perform operation on the database"+operationFailureExp);
		} catch (DataBaseAccessException dbAccessFailureExp) {
			LOGGER_OBJ.error("unable to access the database"+dbAccessFailureExp);
		} catch (InvalidDBRecordFormatException exp) {
			LOGGER_OBJ.error("format of database record is invalid"+exp);
		}
		LOGGER_OBJ.debug("execution of ProductAvailabilityService() started");
	}

	/**
	 * Finds the products which are available in the city of the user.
	 * 
	 * @param userId id of the user.
	 * @return the list of products which are available in the city of the user.
	 * @throws DataBaseAccessException     when unable to access the database.
	 * @throws DBOperationFailureException when operation on the database fails.
	 */
	public List<Product> getAllProductForUser(String userId)
			throws DBOperationFailureException, DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getAllProductForUser() started");
		List<Product> productsForUser;

		String userCity = userList.stream().filter(user -> user.getUserId().equalsIgnoreCase(userId))
				.map(user -> user.getCity()).collect(Collectors.toList()).get(0);

		productsForUser = productList.stream().filter(product -> product.getAvailableCity().contains(userCity))
				.collect(Collectors.toList());
		LOGGER_OBJ.debug("execution of getAllProductForUser() started");
		return productsForUser;
	}

	/**
	 * Finds the products which match the category.
	 * 
	 * @param userId          id of the user.
	 * @param productCategory category of the user.
	 * @return the list of products which match the given category.
	 * @throws DataBaseAccessException     when unable to access the database.
	 * @throws DBOperationFailureException when operation on the database fails.
	 */
	public List<Product> getAllProductForUserForCategory(String userId, String productCategory)
			throws DBOperationFailureException, DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getAllProductForUserForCategory() started");
		List<Product> productsInUserCity = getAllProductForUser(userId);
		List<Product> allProductForUser = productsInUserCity.stream()
				.filter(product -> product.getProductCategory().equalsIgnoreCase(productCategory))
				.collect(Collectors.toList());
		LOGGER_OBJ.debug("execution of getAllProductForUserForCategory() completed");
		return allProductForUser;
	}

}