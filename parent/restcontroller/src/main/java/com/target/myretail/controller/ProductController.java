package com.target.myretail.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.target.myretail.exception.ServiceException;
import com.target.myretail.exception.Status;
import com.target.myretail.response.BaseResponse;
import com.target.myretail.response.RetailResponse;
import com.target.myretail.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *
 *
 */
@Api(value = "MyRetailAPI", description = "Retrieve/Modify Product Information by product id")
@RestController
@RequestMapping("/products/")
public class ProductController {

	@Inject
	private ProductService productService;
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	/**
	 * GET Method to get detailed information about product
	 * @throws ServiceException 
	 */

	@ApiOperation(value = "Gets the product and price information by product id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success response"),
			@ApiResponse(code = 404, message = "Product not found") })
	@HystrixCommand(fallbackMethod = "getProductDataFallBack", groupKey = "getProductData", commandKey = "getProductData", threadPoolKey = "getProductDataPool", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") })
	@ResponseBody
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RetailResponse> getProductData(@PathVariable("id") Integer id) throws ServiceException {

		RetailResponse retailResponse = productService.getProductDetails(id);

		return ResponseEntity.status(retailResponse.getHttpStatus()).body(retailResponse);

	}

	public ResponseEntity<RetailResponse> getProductDataFallBack(Integer id, Throwable e) {

		logger.error("Issue in getProductData()===> ",e);
		RetailResponse retailResponse = new RetailResponse();
		retailResponse.setMessage("Unable to fetch product details. Please try after some time.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(retailResponse);
	}

	/**
	 * PUT Method to update the price information for a product.
	 */

	@ApiOperation(value = "Modifies the product information")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "ProductId in request header and body doesn't match") })
	@HystrixCommand(fallbackMethod = "updateProductFallBack", groupKey = "updateProduct", commandKey = "updateProduct", threadPoolKey = "updateProductPool", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") })
	@ResponseBody
	@PutMapping(value = "/{id}", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> updateProductData(@PathVariable("id") Integer id,
			@RequestBody @Valid RetailResponse retailResponse) {

		if (!id.equals(retailResponse.getId())) {

			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setMessage("ProductId in request header and body doesn't match");
			baseResponse.setStatus(Status.FAILURE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
			
		}
		RetailResponse updatePriceResponse = productService.updateProductDetails(retailResponse);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatePriceResponse);
	}

	public ResponseEntity<BaseResponse> updateProductFallBack(Integer id, RetailResponse retailResponse, Throwable e) {

		logger.error("Issue in updateProductData()===> ",e);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage("Unable to update product details. Please try after some time.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
	}

	/**
	 * POST Method to create the price information for a product.
	 */

	@ApiOperation(value = "Creates the product information")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Created"),
			@ApiResponse(code = 400, message = "Unable to save the data.") })
	@HystrixCommand(fallbackMethod = "createProductFallBack", groupKey = "createProduct", commandKey = "createProduct", threadPoolKey = "createProductPool", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") })
	@ResponseBody
	@PostMapping(value = "/{id}", produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> createProductData(@PathVariable("id") Integer id,
			@RequestBody @Valid RetailResponse retailResponse) {

		if (!id.equals(retailResponse.getId())) {

			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setMessage("ProductId in request header and body doesn't match");
			baseResponse.setStatus(Status.FAILURE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
			
		}

		return ResponseEntity.status(HttpStatus.OK).body(productService.createProductDetails(retailResponse));
	}

	public ResponseEntity<BaseResponse> createProductFallBack(Integer id, RetailResponse retailResponse, Throwable e) {

		logger.error("Issue in createProductData()===> ",e);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage("Unable to create product. Please try after some time.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
	}

	/**
	 * GET Method to get detailed information about product
	 */
	@ApiOperation(value = "Searches  the product information between two prices.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success response"),
			@ApiResponse(code = 400, message = "Unable to serve the request") })
	@HystrixCommand(fallbackMethod = "searchProductFallBack", groupKey = "searchProduct", commandKey = "searchProduct", threadPoolKey = "searchProductPool", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") })
	@ResponseBody
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object[]> searchProduct(@RequestParam("pricelow") Double lowPrice,
			@RequestParam("pricehigh") Double highPrice) {

		if(Objects.isNull(lowPrice)||Objects.isNull(highPrice)) {
			
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setMessage("Both the query params are mandatory in the request.");
			baseResponse.setStatus(Status.FAILURE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Arrays.asList(baseResponse).toArray());

		}
		List<BaseResponse> baseresponse = productService.searchProductDetails(lowPrice, highPrice);

		return ResponseEntity.status(HttpStatus.OK).body(baseresponse.toArray());

	}

	public ResponseEntity<Object[]> searchProductFallBack(Double lowPrice, Double highPrice, Throwable e) {

		logger.error("Issue in searchProduct()===> ",e);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage("Unable to search product details. Please try after some time.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Arrays.asList(baseResponse).toArray());
	}

}
