package com.devsuperior.dscatalog.resource.exceptios;

import java.util.ArrayList;
import java.util.List;

public class ValidatetionError extends StandardError {
	
	private static final long serialVersionUID = 1L;
	
	private List<FielddMessage> errors = new ArrayList<>();

	public List<FielddMessage> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String message) {
		errors.add(new FielddMessage(fieldName, message));
	}
	
}
