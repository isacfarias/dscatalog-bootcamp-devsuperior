package com.devsuperior.dscatalog.resource;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> categorias = service.findAll();
		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathParam("id") Long id) {
		Optional<Category> categoria = service.findById(id);
		return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Category> criarCategoria(@RequestBody Category categoria) {
		Category temp =  service.save(categoria);
		return temp != null ? ResponseEntity.ok(temp) : ResponseEntity.notFound().build();
	}
	
	@PutMapping
	public ResponseEntity<Category> atualizarCategoria(@RequestBody Category categoria) {
		Category temp =  service.save(categoria);
		return temp != null ? ResponseEntity.ok(temp) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id) {
		service.delete(id);
	}
}
