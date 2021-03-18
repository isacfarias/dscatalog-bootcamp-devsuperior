package com.devsuperior.dscatalog.resource.exceptios;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError error = resultStandardError(e,
                                                  request,
                                                  status,
                                                  "Resource not found");
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError error = resultStandardError(e,
				                                  request,
				                                  status,
				                                  "Database exception");
		
		return ResponseEntity.status(status).body(error);
	}

	private StandardError resultStandardError(Exception e, HttpServletRequest request, HttpStatus status, String msg) {
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(msg);
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return error;
	}
	
	
	

}
