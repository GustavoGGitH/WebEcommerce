package com.GG.springboot.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GG.springboot.app.entity.Order;
import com.GG.springboot.app.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>  {

	Order save(Order order);

}
