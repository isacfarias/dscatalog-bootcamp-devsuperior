package com.devsuperior.dscatalog.services;

import java.util.Iterator;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositoy.RoleRepository;
import com.devsuperior.dscatalog.repositoy.UserRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
		return userRepository.findAll(pageRequest).map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new UserDTO(user);				
	}

	@Transactional
	public UserDTO save(UserDTO dto) {
		User user = new User();
		copyDtoToEntity(dto, user);
		return new UserDTO(userRepository.save(user));
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User user = userRepository.getOne(id);
			copyDtoToEntity(dto, user);
			return new UserDTO(userRepository.save(user));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		}
	}

	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integridade violada: "+id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		}
	}
	
	private void copyDtoToEntity(UserDTO dto, User user) {
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.getRoles().clear();
		for (RoleDTO roleDTO : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDTO.getId());
			user.getRoles().add(role);								
		}
	}
	
}
