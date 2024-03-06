package com.GG.springboot.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GG.springboot.app.entity.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
	Optional<Usuario> findByUsername(String username);

}
