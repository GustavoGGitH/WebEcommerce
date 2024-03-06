package com.GG.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.GG.springboot.app.service.IUploadFileService;

@SpringBootApplication


public class SpringbootPorfolioWebcampingApplication {
	
	@Autowired
	IUploadFileService uploadFileService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPorfolioWebcampingApplication.class, args);
	}

}
