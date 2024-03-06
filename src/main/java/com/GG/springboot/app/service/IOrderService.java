package com.GG.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.GG.springboot.app.entity.Order;
import com.GG.springboot.app.entity.Usuario;

@Service
public interface IOrderService {

	public Order saveOrder(Order order);

	// permite generar autonumérico
	String generarNumeroOrden();

	public List<Order> findAllOrder();
	
	public Optional<Order> findById(Integer id);
	
	 List<Order> findByUser(Usuario user);

}
