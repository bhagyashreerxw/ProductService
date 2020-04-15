package com.reactiveworks.productservice.service;

import java.util.List;

import com.reactiveworks.productservice.db.exceptions.DBOperationFailureException;
import com.reactiveworks.productservice.db.exceptions.DataBaseAccessException;
import com.reactiveworks.productservice.model.Product;

/**
 * Provides product services to the user.
 */
public interface IProductAvailabilityService {

	/**
	 * Finds the products which are available in the city of the user.
	 * @param userId id of the user. 
	 * @return the list of products which are available in the city of the user.
	 */
	 public List<Product> getAllProductForUser(String userId)  throws DBOperationFailureException, DataBaseAccessException;
     
	 /**
	  * Finds the products which match the category.
	  * @param userId id of the user.
	  * @param productCategory category of the user.
	  * @return the list of products which match the given category.
	  */
	 public List<Product> getAllProductForUserForCategory(String userId,String productCategory) throws DBOperationFailureException, DataBaseAccessException;
	
	
}
