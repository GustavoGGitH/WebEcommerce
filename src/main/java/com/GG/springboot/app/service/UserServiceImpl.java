package com.GG.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.GG.springboot.app.dao.UsuarioRepository;
import com.GG.springboot.app.entity.Usuario;

@Service
public class UserServiceImpl implements IUserService {


	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UsuarioRepository userRepository;

	@Override
	public Optional<Usuario> findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}
	@Override
	public Optional<Usuario> findById(Integer id) {
		return Optional.ofNullable(userRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found ", id))));
	}

	@Override
	public Usuario saveUser(Usuario user) {
		logger.info("Usuario dentro de saveUser en el servicio:  {}", user);
		return userRepository.save(user);

	}

	@Override
	public List<Usuario> findAll() {
		return userRepository.findAll();
	}

}
