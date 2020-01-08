package com.target.myretail.service;

import java.util.List;

import com.target.myretail.Model.CurrentPrice;
import com.target.myretail.exception.ServiceException;
import com.target.myretail.response.BaseResponse;
import com.target.myretail.response.RetailResponse;

public interface ProductService {
	
	
	
	public RetailResponse getProductDetails(Integer id) throws ServiceException;
	

	public RetailResponse updateProductDetails(RetailResponse retailResponse);


	public RetailResponse createProductDetails(RetailResponse retailResponse);


	public List<BaseResponse> searchProductDetails(Double lowPrice, Double highPrice);
	
	public  String getProductCurrentPrice(Integer productId) throws ServiceException;
	
	public String getProductDescription(Integer productId) throws ServiceException;


}
