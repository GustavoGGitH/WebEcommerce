package com.GG.springboot.app.service;

import org.springframework.stereotype.Service;

import com.GG.springboot.app.entity.OrderDetail;



@Service
public interface IOrderDetailService {
	
	//Solamente declaro el método , en este caso es saveOrderDetail que permite guardar la entidad Order Detail
	OrderDetail saveOrderDetail(OrderDetail orderDetail);
	

}
