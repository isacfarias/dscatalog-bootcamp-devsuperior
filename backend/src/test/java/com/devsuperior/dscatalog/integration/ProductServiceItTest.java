package com.devsuperior.dscatalog.integration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.factory.CategoryFactory;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@SpringBootTest @Transactional
public class ProductServiceItTest {

	@Autowired
	private ProductService service;
	
	private Long productId;
	private Long noExistproductId;
	private Long countTotalProducts;
	private Long countPCGamerProducts;
	private Long countComputadoresCategory;
	private PageRequest pageRequest;
	
	@BeforeEach
	void setUp() {
		productId = 1L;
		noExistproductId = 1000L;
		countTotalProducts = 25L;
		countPCGamerProducts = 21L;
		pageRequest = PageRequest.of(0, 10);
		countComputadoresCategory = 23L;
	}
	
	@Test
	void deleteShouldDoNothingWhenIdExists() {
		assertDoesNotThrow(() -> service.delete(productId));
	}
	
	@Test
	void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNoExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.delete(noExistproductId));
	}
	
	@Test
	void findAllPagedShouldReturnNothingWhenNameDoesNoExist() {
		
		final var name = "Camera";
		var result = service.findAllPaged(0L, name, pageRequest);
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	void findAllPagedShouldReturnProductsWhenNameExists() {
		final var name = "PC Gamer";
		var result = service.findAllPaged(0L, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countPCGamerProducts, result.getTotalElements());
	}
	

	@Test
	void findAllPagedShouldReturnProductsWhenNameExistigIgnoreCase() {
		final var name = "pc Gamer";
		
		var result = service.findAllPaged(0L, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countPCGamerProducts, result.getTotalElements());
	}
	
	@Test
	void findAllPagedShouldReturnAllProductsWhenNameisEmpty() {
		final var name = "";
		
		var result = service.findAllPaged(0L, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countTotalProducts, result.getTotalElements());
	}
	
	@Test
	void findAllPagedShouldReturnAllProductsWhenCategory() {
		final var name = "";
		final var category = CategoryFactory.createCategoryComputers().getId();
		
		var result = service.findAllPaged(category, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countComputadoresCategory, result.getTotalElements());
	}
	
	@Test
	void findAllPagedShouldReturnAllProductsWhenCategoryIgnoreCase() {
		final var name = "";
		final var category = CategoryFactory.createCategoryComputersIgnorecase().getId();
		
		var result = service.findAllPaged(category, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countComputadoresCategory, result.getTotalElements());
	}
	

}
