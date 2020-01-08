package com.target.myretail.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.target.myretail.Model.CurrentPrice;
import com.target.myretail.domain.Currency;
import com.target.myretail.exception.ServiceException;
import com.target.myretail.exception.Status;
import com.target.myretail.repository.Product;
import com.target.myretail.repository.ProductRepository;
import com.target.myretail.response.BaseResponse;
import com.target.myretail.response.RetailResponse;

@Service
@Scope("prototype")
@PropertySource(value = "classpath:config.properties")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${target.product.details.api}")
	private String url;

	@Value("${target.product.details.excludes}")
	private String excludes;

	@Value("${target.product.price.api}")
	private String priceAPI;
	@Inject
	private ProductRepository productRepository;

	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public RetailResponse getProductDetails(Integer productId) throws ServiceException {

		RetailResponse retailResponse = new RetailResponse();

		String productDescription = this.getProductDescription(productId);
		if (Objects.isNull(productDescription)) {

			retailResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
			retailResponse.setStatus(Status.FAILURE);
			retailResponse.setMessage("No information Available in the Product Details Response for " + productId);
			return retailResponse;
		}

		String productTitle = fetchTitleDetails(productDescription);
		if (Objects.isNull(productTitle)) {

			retailResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
			retailResponse.setStatus(Status.FAILURE);
			retailResponse
					.setMessage("No title information Available in the Product Details Response for " + productId);
			return retailResponse;

		}

		retailResponse.setId(productId);
		retailResponse.setHttpStatus(HttpStatus.OK);
		/*This code will not work as API so manually setting current price for GET API , kind of API mock
		 * String productPrice = getProductCurrentPrice(productId); if
		 * (Objects.isNull(productPrice)) {
		 * 
		 * retailResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
		 * retailResponse.setStatus(Status.FAILURE);
		 * retailResponse.setMessage("No price details available for the product" +
		 * productId); return retailResponse; }
		 */
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setCurrency_code(Currency.USD.toString());
		currentPrice.setValue(Double.valueOf(1234.787));
		retailResponse.setCurrent_price(currentPrice);
		retailResponse.setName(productTitle);
		return retailResponse;

	}

	/**
	 * 
	 * This API is not working, so just coded resttemplate to fetch the data as
	 * MOCK.
	 * 
	 * @throws ServiceException
	 */
	@Override
	public String getProductCurrentPrice(Integer productId) throws ServiceException {

		ResponseEntity<String> json = null;
		try {
			json = restTemplate.getForEntity(priceAPI.replace("{productId}", productId.toString()), String.class);
		} catch (HttpClientErrorException ex) {

			if (ex.getRawStatusCode() == 404) {
				return null;
			} else {
				throw new ServiceException(ex);
			}
		}

		return json.getBody();

	}

	/**
	 * 
	 *
	 */
	@Override
	public String getProductDescription(Integer productId) throws ServiceException {

		ResponseEntity<String> json = null;
		try {
			json = restTemplate.getForEntity(new StringBuilder(url).append(productId).append(excludes).toString(),
					String.class);
		} catch (HttpClientErrorException ex) {

			if (ex.getRawStatusCode() == 404) {
				return null;
			} else {
				throw new ServiceException(ex);
			}
		}

		return json.getBody();

	}

	private String fetchTitleDetails(String body) {

		JSONObject jsonObject = new JSONObject(body);
		JSONObject productDesc = null;
		try {

			JSONObject product = jsonObject.getJSONObject("product");
			JSONObject item = product.getJSONObject("item");
			productDesc = item.getJSONObject("product_description");
		} catch (Exception e) {

			logger.error("No Product description information for this request:::" + body);
			return null;
		}

		return productDesc.getString("title");

	}

	@Override
	public RetailResponse updateProductDetails(RetailResponse retailResponse) {

		Optional<Product> productInDB = productRepository.findById(retailResponse.getId());
		if (!productInDB.isPresent()) {
			createAndSaveProduct(retailResponse);
		} else {

			Product product = productInDB.get();
			product.setPrice(retailResponse.getCurrent_price().getValue());
			productRepository.save(product);
		}

		return retailResponse;
	}

	@Override
	public RetailResponse createProductDetails(RetailResponse retailResponse) {

		RetailResponse retailresponse = new RetailResponse();
		Optional<Product> productInDB = productRepository.findById(retailResponse.getId());
		if (productInDB.isPresent()) {

			retailresponse.setStatus(Status.FAILURE);
			retailresponse.setMessage("Product already exists in DB.");
			return retailresponse;
		}

		createAndSaveProduct(retailResponse);
		retailresponse.setStatus(Status.SUCCESS);
		retailresponse.setMessage("Product created in DB.");
		return retailresponse;
	}

	@Override
	public List<BaseResponse> searchProductDetails(Double lowPrice, Double highPrice) {

		BaseResponse baseResponse = new BaseResponse();
		List<Product> productsListInDB = productRepository.findByPriceBetween(lowPrice, highPrice);
		if (productsListInDB.isEmpty()) {

			baseResponse.setStatus(Status.FAILURE);
			baseResponse
					.setMessage("No Products exists in DB for the prices between " + lowPrice + " and " + highPrice);
			return Arrays.asList(baseResponse);
		}

		return productsListInDB.stream().map(x -> {

			RetailResponse retailResponse = new RetailResponse();
			retailResponse.setId(x.getId());
			retailResponse.setName(x.getName());
			CurrentPrice currentPrice = new CurrentPrice();
			currentPrice.setCurrency_code(x.getCurrency().toString());
			currentPrice.setValue(x.getPrice());
			retailResponse.setCurrent_price(currentPrice);
			return retailResponse;

		}).collect(Collectors.toList());

	}

	private void createAndSaveProduct(RetailResponse retailResponse) {

		Product product = new Product();
		product.setCurrency(Currency.valueOf(retailResponse.getCurrent_price().getCurrency_code()));
		product.setId(retailResponse.getId());
		product.setName(retailResponse.getName());
		product.setPrice(retailResponse.getCurrent_price().getValue());
		productRepository.save(product);

	}

}
