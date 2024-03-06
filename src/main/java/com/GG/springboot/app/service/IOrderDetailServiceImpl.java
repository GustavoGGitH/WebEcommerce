package com.GG.springboot.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GG.springboot.app.dao.OrderDetailRepository;
import com.GG.springboot.app.entity.OrderDetail;



@Service
public class IOrderDetailServiceImpl implements IOrderDetailService {
	
	@Autowired
	OrderDetailRepository detailRepository;

	@Override
	public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
		return detailRepository.save(orderDetail);
	}
	
}
