package com.GG.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.GG.springboot.app.entity.Categorie;
import com.GG.springboot.app.entity.Product;

@Service
public interface ICategorieService {

	public List<Categorie> categorieFindAll();

	public Page<Categorie> categoriePageFindAll(Pageable pageable);

	public Categorie findById(int id);

	
	public List<Product> allProducts(Integer id);
	
	public Page<Product> obtenerProductosPorCategoria(Categorie categorie, Pageable pageable);

	public void save(Categorie categorie);
	
	public void delete(Integer id);

}
