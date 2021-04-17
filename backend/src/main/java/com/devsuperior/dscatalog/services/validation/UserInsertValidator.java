package com.devsuperior.dscatalog.services.validation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositoy.UserRepository;
import com.devsuperior.dscatalog.resource.exceptios.FielddMessage;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserInsertValid ann) {}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FielddMessage> list = new ArrayList<>();
		
		User user = repository.findByEmail(dto.getEmail());		
		if (user != null ) {
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