package com.devsuperior.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.ProductFactory;
import com.devsuperior.dscatalog.repositoy.ProductRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private Long productId;
	private Long noExistproductId;
	private Long dependenteProductId;
	private PageImpl<Product> page;
	
	@BeforeEach
	void setUp() {
		productId = 2L;
		noExistproductId = 1000L;
		dependenteProductId = 4L;
		final var product = ProductFactory.createProduct(productId);
		final var productNoExist = ProductFactory.createProduct(noExistproductId);
		page = new PageImpl<>(List.of(product));
		
		when(repository.save(ProductFactory.createProduct())).thenReturn(product);
		when(repository.find(any(), anyString(), any())).thenReturn(page);
		when(repository.findById(productId)).thenReturn(Optional.of(product));
		when(repository.findById(noExistproductId)).thenReturn(Optional.empty());
		
		doNothing().when(repository).deleteById(productId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(noExistproductId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependenteProductId);
		doThrow(EntityNotFoundException.class).when(repository).save(productNoExist);
	}
	
	@Test
	void deleteShouldDoNothingWhenIdExists() {
		assertDoesNotThrow(() -> service.delete(productId));
		verify(repository, times(1)).deleteById(productId);
	}
	
	@Test
	void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNoExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.delete(noExistproductId));
		verify(repository, times(1)).deleteById(noExistproductId);
	}
	
	@Test
	void deleteShouldThrowDataBaseExceptionWhenDependenteId() {
		assertThrows(DataBaseException.class, () -> service.delete(dependenteProductId));
		verify(repository, times(1)).deleteById(dependenteProductId);
	}
	
	
	@Test @Disabled
	void findShouldReturnListProductsWhenSucess() {
		
		var page = service.findAllPaged(null, "PC gamer", null);
		
		verify(repository, times(1)).find(any(), anyString(), any());
	}
	
	@Test
	void findbyIdShouldReturnProductsWhenIdExist() {
		var product = service.findById(productId);
		verify(repository, times(1)).findById(productId);
		
		assertNotNull(product);
		assertEquals(productId, product.getId());
	}
	
	@Test
	void findbyIdShouldThrowResourceNotFoundExceptionWhenIdNoExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.findById(noExistproductId));
		verify(repository, times(1)).findById(noExistproductId);
	}
	
	@Test @Disabled
	void updateShouldReturnProductsWhenIdExist() {
		var product = service.update(productId, ProductFactory.createProductDTO(productId));
		verify(repository, times(1)).save(any());
		
		assertNotNull(product);
		assertEquals(productId, product.getId());
	}
	
	@Test @Disabled
	void updateShouldThrowResourceNotFoundExceptionWhenIdNoExist() {
		var productNotupdated = ProductFactory.createProductDTO(noExistproductId);
		assertThrows(ResourceNotFoundException.class, () -> service.update(noExistproductId, productNotupdated));
		verify(repository, times(1)).save(any());
	}
	
}
