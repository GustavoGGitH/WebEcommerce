package com.GG.springboot.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "categories")
public class Categorie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	
	@Column
	@NotBlank(message = "Description cant not be empty")
	public String desc_categorie;

	@Column
	public String foto;

	@OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

	private List<Product> products;

	public Categorie() {
		this.products = new ArrayList<Product>();
	}

	public Categorie(int id, String desc_categorie, String foto, List<Product> products) {
		super();
		this.id = id;
		this.desc_categorie = desc_categorie;
		this.foto = foto;
		this.products = products;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc_categorie() {
		return desc_categorie;
	}

	public void setDesc_categorie(String desc_categorie) {
		this.desc_categorie = desc_categorie;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	

	@Override
	public String toString() {
		return desc_categorie;
	}



	private static final long serialVersionUID = 1L;

}
