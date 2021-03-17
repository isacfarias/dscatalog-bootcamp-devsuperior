package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositoy.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	public List<Category> findAll() {
		List<Category> categorias = repository.findAll();
		return categorias;
	}

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
