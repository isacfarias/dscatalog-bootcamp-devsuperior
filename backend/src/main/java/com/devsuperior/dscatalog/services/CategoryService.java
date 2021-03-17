package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositoy.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<CategoryDTO> categorias = repository.findAll()
				                                 .stream()
				                                 .map(category -> new CategoryDTO(category))
				                                 .collect(Collectors.toList());
		return categorias;
	}

	@Transactional(readOnly = true)
	public Optional<CategoryDTO> findById(Long id) {
		return Optional.of(new CategoryDTO(repository.findById(id).get()));
	}
	
	public CategoryDTO save(Category category) {
		return new CategoryDTO(repository.save(category));
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	
	
}
