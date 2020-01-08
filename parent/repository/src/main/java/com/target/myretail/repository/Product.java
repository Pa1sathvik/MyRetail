package com.target.myretail.repository;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import com.target.myretail.domain.Currency;

@Entity
@Table(name = "product")
@DynamicInsert
public class Product {

	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name",length = 50)
	private String name;
	
	@Column(name = "price",precision = 6, scale = 2)
	private Double price;
	
	@Column(name = "currency")
	@Enumerated(EnumType.ORDINAL)
	private Currency currency;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ROW_CREATED", nullable = false)
	private Date rowCreated;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ROW_UPDATED", nullable = false)
	private Date rowUpdated;

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Date getRowCreated() {
		return rowCreated;
	}

	public void setRowCreated(Date rowCreated) {
		this.rowCreated = rowCreated;
	}

	public Date getRowUpdated() {
		return rowUpdated;
	}

	public void setRowUpdated(Date rowUpdated) {
		this.rowUpdated = rowUpdated;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", currency=" + currency + ", rowCreated="
				+ rowCreated + ", rowUpdated=" + rowUpdated + "]";
	}

	
}
