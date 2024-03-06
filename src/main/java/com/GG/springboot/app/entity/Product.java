package com.GG.springboot.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Table(name = "Products")
@Entity
public class Product implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@Column
	@NotBlank(message = "Description cant not be empty")
	public String desc_product;

	@Column
	public String desc_extend;

	@Column
	public String foto;

	@Column
	@NotNull(message = "Price cant not be empty")
    @DecimalMin(value = "0.0", message = "The price must be bigger or equal to 0")

	public Float precio;

	@ManyToOne(fetch = FetchType.LAZY)
	private Categorie categorie;
	
	public Product() {
		super();

	}

	public Product(int id, String desc_product, String desc_expand, String foto, Float precio) {
		super();
		this.id = id;
		this.desc_product = desc_product;
		this.desc_extend = desc_expand;
		this.foto = foto;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc_product() {
		return desc_product;
	}

	public void setDesc_product(String desc_product) {
		this.desc_product = desc_product;
	}



	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	
	
	
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	


	public String getDesc_extend() {
		return desc_extend;
	}

	public void setDesc_extend(String desc_extend) {
		this.desc_extend = desc_extend;
	}

	@Override
	public String toString() {
		return desc_product;
	}



	private static final long serialVersionUID = 1L;

}
