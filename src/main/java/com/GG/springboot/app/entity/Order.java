package com.GG.springboot.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;





@Entity
@Table(name="orders")
public class Order implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String number;

    @Column
    private Date datecreate;

    @Column
    private Date datereceipt;

    @Column
    private double Total;	
    
    
	@PrePersist
	public void prePersist() {

		datecreate = new Date();
		datereceipt = new Date();
	}
	
	
    @ManyToOne
    @JoinColumn(name = "user_id") 
    private Usuario usuario;
	 
	
	// establecemos relación entre la factura y sus líneas, recordemos que no es
	// necesario a la inversa, es decir linea-facturas
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// hago join un campo clave foránea en ItemFacturas, tabla facturaItems de la
	// relación
	// al no ser un relación en ambos sentidos hay que especificar si o si la clave
	// foránea, es la diferencia con mappedby
	// RELACION UNIDIRECCIONAL
	@JoinColumn(name = "order_id")

	private List<OrderDetail> detail;

	// acá genero el constructor

	public Order() {
		this.detail = new ArrayList<OrderDetail>();
	}


	public Order(Integer id, String number, Date datecreate, Date datereceipt, double total, List<OrderDetail> detail) {
		super();
		this.id = id;
		this.number = number;
		this.datecreate = datecreate;
		this.datereceipt = datereceipt;
		Total = total;
		this.detail = detail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDatecreate() {
		return datecreate;
	}

	public void setDatecreate(Date datecreate) {
		this.datecreate = datecreate;
	}

	public Date getDatereceipt() {
		return datereceipt;
	}

	public void setDatereceipt(Date datereceipt) {
		this.datereceipt = datereceipt;
	}

	public double getTotal() {
		return Total;
	}

	public void setTotal(double total) {
		Total = total;
	}

	public List<OrderDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<OrderDetail> detail) {
		this.detail = detail;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
	

	


}
