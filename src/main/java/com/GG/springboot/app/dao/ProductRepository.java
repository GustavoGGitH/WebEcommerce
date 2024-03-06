package com.GG.springboot.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GG.springboot.app.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

}
