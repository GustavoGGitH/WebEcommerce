package com.GG.springboot.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GG.springboot.app.entity.Categorie;
import com.GG.springboot.app.entity.Product;


@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
	
	// Obtiene pagína de productos según un categoría en particular
	@Query("SELECT p FROM Product p WHERE p.categorie = :categorie")
	public Page<Product> findProductsByPageCateg(Categorie categorie, Pageable pageable);
	
	
	// Obtiene pagína de productos según un categoría en particular
	@Query("SELECT p FROM Product p WHERE p.categorie.id = ?1")
	public List<Product> findProductsByCateg(Integer id);
}

