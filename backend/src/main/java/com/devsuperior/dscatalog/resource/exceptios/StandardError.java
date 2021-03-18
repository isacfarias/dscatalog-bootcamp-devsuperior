package com.devsuperior.dscatalog.resource.exceptios;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {

	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
	

}
