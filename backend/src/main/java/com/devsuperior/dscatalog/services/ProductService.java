package com.devsuperior.dscatalog.services;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositoy.CategoryRepository;
import com.devsuperior.dscatalog.repositoy.ProductRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;


	// return productRepository.find(categories, name.trim(), pageRequest).map(ProductDTO::new);
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Long categoryId, String name, PageRequest pageRequest) {
		List<Category> categories = (categoryId == 0) ? null : Arrays.asList(categoryRepository.getOne(categoryId));
		Page<Product> products = productRepository.find(categories, name.trim(), pageRequest);
		productRepository.findProductsWithCategories(products.getContent());
		return products.map(prod -> new ProductDTO(prod, prod.getCategories()));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new ProductDTO(product, product.getCategories());

	}

	@Transactional
	public ProductDTO save(ProductDTO dto) {
		Product product = new Product();
		copyDtoToEntity(dto, product);
		return new ProductDTO(productRepository.save(product));
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product product = productRepository.getOne(id);
			copyDtoToEntity(dto, product);
			return new ProductDTO(productRepository.save(product));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		}
	}

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integridade violada: "+id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		}
	}

	private void copyDtoToEntity(ProductDTO dto, Product product) {
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setImgUrl(dto.getImgUrl());
		product.setDate(dto.getDate());
		product.getCategories().clear();

		dto.getCategories().forEach(categoryDto -> {
			Category category = categoryRepository.getOne(categoryDto.getId());
			product.getCategories().add(category);
		});

	}
}
