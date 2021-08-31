package com.devsuperior.dscatalog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.CategoryFactory;
import com.devsuperior.dscatalog.factory.ProductFactory;
import com.devsuperior.dscatalog.repositoy.ProductRepository;


@DataJpaTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository reposity;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProducts;
	private Long countPCGamerProducts;
	private Long countComputadoresCategory;
	private PageRequest pageRequest;
	
	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
		countPCGamerProducts = 21L;
		pageRequest = PageRequest.of(0, 10);
		countComputadoresCategory = 23L;
	}
	
	@Test
	void deleteShouldDeleteObjectWhenExists() {
		reposity.deleteById(existingId);
		Optional<Product> result = reposity.findById(existingId);
		assertFalse(result.isPresent());
	}
	
	@Test
	void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
		assertThrows(EmptyResultDataAccessException.class, () -> reposity.deleteById(nonExistingId));
	}
	
	@Test
	void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		var product = reposity.save(ProductFactory.createProduct());
		Optional<Product> result = reposity.findById(product.getId());
		assertNotNull(product.getId());
		assertTrue(result.isPresent());
		assertEquals(countTotalProducts+1, product.getId());
		assertSame(result.get(), product);
	}
	

	@Test
	void findShouldReturnProductsWhenNameExists() {
		final var name = "PC Gamer";
		Page<Product> result = reposity.find(null, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countPCGamerProducts, result.getTotalElements());
	}
	

	@Test
	void findShouldReturnProductsWhenNameExistigIgnoreCase() {
		final var name = "pc Gamer";
		
		Page<Product> result = reposity.find(null, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countPCGamerProducts, result.getTotalElements());
	}
	
	@Test
	void findShouldReturnAllProductsWhenNameisEmpty() {
		final var name = "";
		
		Page<Product> result = reposity.find(null, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countTotalProducts, result.getTotalElements());
	}
	
	@Test
	void findShouldReturnAllProductsWhenCategory() {
		final var name = "";
		final var category = List.of(CategoryFactory.createCategoryComputers());
		
		Page<Product> result = reposity.find(category, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countComputadoresCategory, result.getTotalElements());
	}
	
	@Test
	void findShouldReturnAllProductsWhenCategoryIgnoreCase() {
		final var name = "";
		final var category = List.of(CategoryFactory.createCategoryComputersIgnorecase());
		
		Page<Product> result = reposity.find(category, name, pageRequest);
		
		assertFalse(result.isEmpty());
		assertEquals(countComputadoresCategory, result.getTotalElements());
	}
	

}
