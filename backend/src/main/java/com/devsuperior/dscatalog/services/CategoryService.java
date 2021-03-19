package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositoy.CategoryRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		return repository.findAll(pageRequest).map(CategoryDTO::new);
	}

	@Transactional(readOnly = true)
	public Optional<CategoryDTO> findById(Long id) {
		return Optional.of(new CategoryDTO(repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado") )));
	}

	@Transactional
	public CategoryDTO save(CategoryDTO categoria) {
		Category category = new Category();
		category.setName(categoria.getName());
		return new CategoryDTO(repository.save(category));
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO categoria) {
		try {
			Category category = repository.getOne(id);
			category.setName(categoria.getName());
			return new CategoryDTO(repository.save(category));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integridade violada: "+id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		}
	}

}
