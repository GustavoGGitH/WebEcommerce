package com.GG.springboot.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.GG.springboot.app.dao.ProductRepository;
import com.GG.springboot.app.entity.Product;

@Service
public class IProductServiceImpl implements IProductService {

	@Autowired
	public ProductRepository productRepository;

	@Override
	public Product findById(int id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public List<Product> findByAll() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	@Transactional
	public Page<Product> AllItems(Pageable pageable) {

		return productRepository.findAll(pageable);
	}

	@Override
	public void save(Product product) {
		productRepository.save(product);

	}

	@Override
	public void delete(int id) {
		productRepository.deleteById(id);
	}

}
