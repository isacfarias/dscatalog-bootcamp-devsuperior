package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositoy.ProductRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
}
