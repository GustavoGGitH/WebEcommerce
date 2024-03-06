package com.GG.springboot.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;





@Entity
@Table(name="order_detail")
public class OrderDetail implements Serializable {


 
	private static final long serialVersionUID = 1938510579505942171L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	
	@Column
	private String name;
	
	@Column
	private double quantity;
	
	@Column
	private double price;
	
	@Column
	private double total;

	@ManyToOne
	@JoinColumn(name = "product_id") 
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order orden;
	

	
	public OrderDetail() {
		super();
		
	}


	public OrderDetail(Integer id, String name, double quantity, double price, double total) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getQuantity() {
		return quantity;
	}


	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}

	

	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", name=" + name + ", quantity=" + quantity + ", price=" + price + ", total="
				+ total + "]";
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	public double calculateAmount() {
		return price * quantity;
		
	}


	public Order getOrden() {
		return orden;
	}


	public void setOrden(Order orden) {
		this.orden = orden;
	}



	
	
	
	
	/*@ManyToOne
	@JoinColumn(name = "orden_id", insertable = false, updatable = false) 
	private Order orden;*/
	/*
	@ManyToOne
	@JoinColumn(name = "cod_articulo") 
	private Product productos;*/
	






	
	
	

}
