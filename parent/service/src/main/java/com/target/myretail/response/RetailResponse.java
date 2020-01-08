package com.target.myretail.response;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.target.myretail.Model.CurrentPrice;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class RetailResponse extends BaseResponse {

	private static final long serialVersionUID = -2237085394092921378L;

	@NotNull(message = "Product Id should not be null")
	private Integer productId;
	
	@NotNull(message = "Product name should not be null")
	private String name;
	
	private CurrentPrice current_price;

	public Integer getId() {
		return productId;
	}

	public void setId(Integer productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CurrentPrice getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(CurrentPrice current_price) {
		this.current_price = current_price;
	}

}
