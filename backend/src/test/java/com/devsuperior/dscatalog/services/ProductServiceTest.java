package com.devsuperior.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.ProductFactory;
import com.devsuperior.dscatalog.repositoy.CategoryRepository;
import com.devsuperior.dscatalog.repositoy.ProductRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private Long productId;
	private Long noExistproductId;
	private Long dependenteProductId;
	private PageImpl<Product> page;
	private Product product;
	private Product productNoExist;
	
	@BeforeEach
	void setUp() {
		productId = 2L;
		noExistproductId = 1000L;
		dependenteProductId = 4L;
		product = ProductFactory.createProduct(productId);
		productNoExist = ProductFactory.createProduct(noExistproductId);
		page = new PageImpl<>(List.of(product));
		
		when(repository.save(any())).thenReturn(product);
		when(repository.find(any(), anyString(), any())).thenReturn(page);
		when(repository.findById(productId)).thenReturn(Optional.of(product));
		when(repository.findById(noExistproductId)).thenReturn(Optional.empty());
		
		when(repository.getOne(productId)).thenReturn(product);
		when(repository.getOne(noExistproductId)).thenThrow(EntityNotFoundException.class);
		
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
	
	
	@Test
	void findAllPagedShouldReturnPage() {
		var categoryId = 0L;
		var name = "";
		var pageResquest = PageRequest.of(0, 10);
		
		var result = service.findAllPaged(categoryId, name, pageResquest);
		assertNotNull(result);
		assertFalse(result.isEmpty());
		
		verify(repository, times(1)).find(null, name, pageResquest);
	}
	
	@Test
	void findbyIdShouldReturnProductsWhenIdExist() {
		var product = service.findById(productId);
		verify(repository, times(1)).findById(productId);
		
		assertNotNull(product);
		assertEquals(productId, product.getId());
		verify(repository, times(1)).findById(productId);
	}
	
	@Test
	void findbyIdShouldThrowResourceNotFoundExceptionWhenIdNoExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.findById(noExistproductId));
	}
	
	@Test 
	void updateShouldReturnProductsWhenIdExist() {
		var dto = ProductFactory.createProductDTO();
		var product = service.update(productId, dto);
		
		assertNotNull(product);
		assertEquals(productId, product.getId());
		
		verify(repository, times(1)).save(any());
	}
	
	@Test 
	void updateShouldThrowResourceNotFoundExceptionWhenIdNoExist() {
		var productNotupdated = new ProductDTO();
		assertThrows(ResourceNotFoundException.class, () -> service.update(noExistproductId, productNotupdated));
	}
	
}
