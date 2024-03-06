package com.GG.springboot.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GG.springboot.app.dao.OrderDetailRepository;
import com.GG.springboot.app.dao.OrderRepository;
import com.GG.springboot.app.entity.Order;
import com.GG.springboot.app.entity.Usuario;

@Service
public class IOrderServiceImpl implements IOrderService {

	@Autowired
	public OrderDetailRepository detailRepository;
	
	@Autowired
	public OrderRepository orderRepository;
	
	@Override
	public Order saveOrder(Order order) {
		// TODO Auto-generated method stub
		return detailRepository.save(order);
	}
	
	public String generarNumeroOrden() {
	    List<Order> ordenes = findAllOrder();
	    int numero = ordenes.isEmpty() ? 1 : ordenes.stream().mapToInt(o -> Integer.parseInt(o.getNumber())).max().orElse(0) + 1;

	    String numeroConcatenado = String.format("%08d", numero);
	    return numeroConcatenado;
	} 
	
	@Override
	public List<Order> findAllOrder() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public Optional<Order> findById(Integer id) {
		// TODO Auto-generated method stub
		return orderRepository.findById(id);
	}

	@Override
	public List<Order> findByUser(Usuario user) {
		
		return orderRepository.findByUsuario(user);
	}


}

