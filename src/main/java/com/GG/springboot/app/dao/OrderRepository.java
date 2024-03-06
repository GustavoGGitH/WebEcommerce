package com.GG.springboot.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GG.springboot.app.entity.Order;
import com.GG.springboot.app.entity.Usuario;

@Repository
public interface OrderRepository extends JpaRepository <Order, Integer> {
	List<Order> findByUsuario(Usuario user);

}
