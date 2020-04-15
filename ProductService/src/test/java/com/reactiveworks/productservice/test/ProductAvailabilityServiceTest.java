package com.reactiveworks.productservice.test;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.reactiveworks.productservice.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.service.implementation.ProductAvailabilityService;

/**
 * Tests the ProductAvailabilityService class.
 */
public class ProductAvailabilityServiceTest {

	private static final Logger LOGGER_OBJ = Logger.getLogger("ProductAvailabilityServiceTest.class");

	/**
	 * 
	 * Checks the working of getAllProductForUser() in negative scenario.
	 * 
	 * @throws DBOperationFailureException when operation on database fails.
	 * @throws DataBaseAccessException     when unable to access the database.
	 */
	@Test
	public void getAllProductForUserServiceTest() throws DBOperationFailureException, DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getAllProductForUserServiceTest() started");
		ProductAvailabilityService productService = new ProductAvailabilityService();
		int actualProductsForUserCount = productService.getAllProductForUser("U1001").size();
		int expectedProductsForUserCount = 7;
		assertTrue(expectedProductsForUserCount == actualProductsForUserCount);
		LOGGER_OBJ.debug("execution of getAllProductForUserServiceTest() completed");
	}

	/**
	 * Checks the working of getAllProductForUser() in negative scenario.
	 * 
	 * @throws DBOperationFailureException when operation on database fails.
	 * @throws DataBaseAccessException     when unable to access the database.
	 */
	@Test
	public void getAllProductForUserServiceFailTest() throws DBOperationFailureException, DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getAllProductForUserServiceFailTest() started");
		ProductAvailabilityService productService = new ProductAvailabilityService();
		int actualProductsForUserCount = productService.getAllProductForUser("U1001").size();
		int expectedProductsForUserCount = 6;
		assertTrue(expectedProductsForUserCount != actualProductsForUserCount);
		LOGGER_OBJ.debug("execution of getAllProductForUserServiceFailTest() completed");

	}

	/**
	 * Checks the working of getAllProductForUserForCategory() in positive scenario.
	 * 
	 * @throws DBOperationFailureException when operation on database fails.
	 * @throws DataBaseAccessException     when unable to access the database.
	 */
	@Test
	public void getAllProductForUserForCategoryServiceTest()
			throws DBOperationFailureException, DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getAllProductForUserForCategoryServiceTest() started");
		ProductAvailabilityService productService = new ProductAvailabilityService();
		int actualProductsOfCategoryCount = productService.getAllProductForUserForCategory("U1001", "Mobile").size();
		int expectedProductsOfCategoryCount = 4;
		assertTrue(actualProductsOfCategoryCount == expectedProductsOfCategoryCount);
		LOGGER_OBJ.debug("execution of getAllProductForUserForCategoryServiceTest() completed");

	}

	/**
	 * Checks the working of getAllProductForUserForCategory() in negative scenario.
	 * 
	 * @throws DBOperationFailureException when operation on database fails.
	 * @throws DataBaseAccessException     when unable to access the database.
	 */
	@Test
	public void getAllProductForUserForCategoryServiceFailTest()
			throws DBOperationFailureException, DataBaseAccessException {
		LOGGER_OBJ.debug("execution of getAllProductForUserForCategoryServiceFailTest() started");
		ProductAvailabilityService productService = new ProductAvailabilityService();
		int actualProductsOfCategoryCount = productService.getAllProductForUserForCategory("U1001", "Mobile").size();
		int expectedProductsOfCategoryCount = 3;
		assertTrue(actualProductsOfCategoryCount != expectedProductsOfCategoryCount);
		LOGGER_OBJ.debug("execution of getAllProductForUserForCategoryServiceFailTest() completed");

	}

}