package com.reactiveworks.productservice.model;

/**
 * This class represents the Product.
 */
public class Product {

	String productId;
	String productName;
	String productCategory;
	int price;
	String availableCity;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAvailableCity() {
		return availableCity;
	}

	public void setAvailableCity(String availableCity) {
		this.availableCity = availableCity;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productCategory="
				+ productCategory + ", price=" + price + ", availableCity=" + availableCity + "]";
	}

}
