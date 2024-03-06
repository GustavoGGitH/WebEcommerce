package com.GG.springboot.app.service;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.GG.springboot.app.dao.CategorieRepository;
import com.GG.springboot.app.entity.Categorie;
import com.GG.springboot.app.entity.Product;




@Service	
public class CategorieServiceImpl implements ICategorieService{
	
	@Autowired
	public CategorieRepository categorieRepository;

	@Override
	@Transactional
	public List<Categorie> categorieFindAll() {
		// TODO Auto-generated method stub
		return categorieRepository.findAll();
	}

	@Override
	@Transactional
	public Page<Categorie> categoriePageFindAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return categorieRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Categorie findById(int id) {
		
		return categorieRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Page<Product> obtenerProductosPorCategoria(Categorie categorie, Pageable pageable) {
		 return categorieRepository.findProductsByPageCateg(categorie, pageable);
	
	}

	@Override
	public void save(Categorie categorie) {
		// TODO Auto-generated method stub
		categorieRepository.save(categorie);
		
	}

	@Override
	public List<Product> allProducts(Integer id) {
		// TODO Auto-generated method stub
		return categorieRepository.findProductsByCateg(id);
	}

	@Override
	public void delete(Integer id) {
		categorieRepository.deleteById(id);
		
	}








	
	
	

}
