package com.devsuperior.dscatalog.factory;

import java.time.Instant;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;

public class ProductFactory {
	
	
	public static Product createProduct() {
		var product = new Product("Phone", "Good Fone", 800.0, "https://img.com/img.png", Instant.parse("2021-10-20T03:00:00Z"));
		product.getCategories().add(CategoryFactory.createCategoryComputers());
		return product;
	}
	
	public static Product createProduct(Long productId) {
		var product = createProduct();
		product.setId(productId);
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		var product = createProduct();
		var dto = new ProductDTO(product, product.getCategories());
		return dto;
	}
	
	public static ProductDTO createProductDTO(Long productId) {
		var product = createProductDTO();
		product.setId(productId);
		return product;
	}

}
