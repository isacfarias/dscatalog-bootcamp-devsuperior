package com.devsuperior.dscatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.dscatalog.services.S3Service;

@SpringBootApplication
public class DscatalogApplication implements CommandLineRunner {

	@Autowired
	private S3Service service;
	
	public static void main(String[] args) {
		SpringApplication.run(DscatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.uploadFile("/home/farias/Imagens/ds_rc_001.png");
	}

}
