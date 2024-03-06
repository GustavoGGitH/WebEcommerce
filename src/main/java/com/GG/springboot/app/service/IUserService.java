package com.GG.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GG.springboot.app.entity.Usuario;


@Service
public interface IUserService {
	

		
		//Obtiene usuario según el id provisto
		public Optional <Usuario> findById(Integer id);

		public Usuario saveUser(Usuario user);
		
	
		
		public List<Usuario> findAll();


		public Optional <Usuario> findByUsername(String username);

}
