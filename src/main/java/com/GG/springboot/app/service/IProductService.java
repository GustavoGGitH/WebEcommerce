package com.GG.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.GG.springboot.app.entity.Product;


@Service
public interface IProductService {
	
	public Product findById(int id);
	
	public List<Product> findByAll();

	public Page<Product> AllItems(Pageable pageable);
	
	public void  save(Product product);
	
	
	public void delete (int id);
}


