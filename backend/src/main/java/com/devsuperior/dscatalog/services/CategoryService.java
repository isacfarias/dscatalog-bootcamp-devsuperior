package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositoy.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<Category> findAll() {
		List<Category> categorias = repository.findAll();
		return categorias;
	}

	@Transactional(readOnly = true)
	public Optional<Category> findById(Long id) {
		return repository.findById(id);
	}
	
	public Category save(Category category) {
		return repository.save(category);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	
	
}
