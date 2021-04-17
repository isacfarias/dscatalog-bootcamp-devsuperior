package com.devsuperior.dscatalog.services.validation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositoy.UserRepository;
import com.devsuperior.dscatalog.resource.exceptios.FielddMessage;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserUpdateValid ann) {}

	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		
		List<FielddMessage> list = new ArrayList<>();
		
		User user = repository.findByEmail(dto.getEmail());		
		if (user != null && user.getId().equals(dto.getId())) {
			list.add(new FielddMessage("email", "Email já existe"));
		}
		
		for (FielddMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			                                              .addPropertyNode(e.getFiledName())
					                                      .addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}