package com.target.myretail.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.target.myretail.Model.CurrentPrice;
import com.target.myretail.config.BaseModuleTest;
import com.target.myretail.domain.Currency;
import com.target.myretail.exception.ServiceException;
import com.target.myretail.repository.Product;
import com.target.myretail.repository.ProductRepository;
import com.target.myretail.response.RetailResponse;

@ContextConfiguration(classes = ProductServiceImpl.class)
@TestPropertySource(locations = {"classpath:config.properties"})
public class ProductServiceImplTest extends BaseModuleTest {
	
	
	@Inject
	private ProductServiceImpl productServiceImpl;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@MockBean
	private ProductRepository productRepository;
	
	
	@Value("${target.product.details.api}")
	private String url;

	private String json = "{\"product\":{\"deep_red_labels\":{\"total_count\":2,\"labels\":[{\"id\":\"twbl94\",\"name\":\"Movies\",\"type\":\"merchandise type\",\"priority\":0,\"count\":1},{\"id\":\"rv3fdu\",\"name\":\"SA\",\"type\":\"relationship type\",\"priority\":0,\"count\":1}]},\"available_to_promise_network\":{\"product_id\":\"13860428\",\"id_type\":\"TCIN\",\"available_to_promise_quantity\":110.0,\"street_date\":\"2011-11-15T06:00:00.000Z\",\"availability\":\"AVAILABLE\",\"online_available_to_promise_quantity\":110.0,\"stores_available_to_promise_quantity\":0.0,\"availability_status\":\"IN_STOCK\",\"multichannel_options\":[],\"is_infinite_inventory\":false,\"loyalty_availability_status\":\"IN_STOCK\",\"loyalty_purchase_start_date_time\":\"1970-01-01T00:00:00.000Z\",\"is_loyalty_purchase_enabled\":false,\"is_out_of_stock_in_all_store_locations\":false,\"is_out_of_stock_in_all_online_locations\":false},\"item\":{\"tcin\":\"13860428\",\"bundle_components\":{},\"dpci\":\"058-34-0436\",\"upc\":\"025192110306\",\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"downstream_description\":\"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\",\"bullet_description\":[\"<B>Movie Studio:</B> Universal Studios\",\"<B>Movie Genre:</B> Comedy\",\"<B>Run Time (minutes):</B> 119\",\"<B>Software Format:</B> Blu-ray\"]},\"buy_url\":\"https://www.target.com/p/the-big-lebowski-blu-ray/-/A-13860428\",\"enrichment\":{\"images\":[{\"base_url\":\"https://target.scene7.com/is/image/Target/\",\"primary\":\"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\",\"content_labels\":[{\"image_url\":\"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\"}]}],\"sales_classification_nodes\":[{\"node_id\":\"hp0vg\"},{\"node_id\":\"5xswx\"}]},\"return_method\":\"This item can be returned to any Target store or Target.com.\",\"handling\":{},\"recall_compliance\":{\"is_product_recalled\":false},\"tax_category\":{\"tax_class\":\"G\",\"tax_code_id\":99999,\"tax_code\":\"99999\"},\"display_option\":{\"is_size_chart\":false},\"fulfillment\":{\"is_po_box_prohibited\":true,\"po_box_prohibited_message\":\"We regret that this item cannot be shipped to PO Boxes.\",\"box_percent_filled_by_volume\":0.27,\"box_percent_filled_by_weight\":0.43,\"box_percent_filled_display\":0.43},\"package_dimensions\":{\"weight\":\"0.18\",\"weight_unit_of_measure\":\"POUND\",\"width\":\"5.33\",\"depth\":\"6.65\",\"height\":\"0.46\",\"dimension_unit_of_measure\":\"INCH\"},\"environmental_segmentation\":{\"is_hazardous_material\":false,\"has_lead_disclosure\":false},\"manufacturer\":{},\"product_vendors\":[{\"id\":\"1984811\",\"manufacturer_style\":\"025192110306\",\"vendor_name\":\"Ingram Entertainment\"},{\"id\":\"4667999\",\"manufacturer_style\":\"61119422\",\"vendor_name\":\"UNIVERSAL HOME VIDEO\"},{\"id\":\"1979650\",\"manufacturer_style\":\"61119422\",\"vendor_name\":\"Universal Home Ent PFS\"}],\"product_classification\":{\"product_type\":\"542\",\"product_type_name\":\"ELECTRONICS\",\"item_type_name\":\"Movies\",\"item_type\":{\"category_type\":\"Item Type: MMBV\",\"type\":300752,\"name\":\"movies\"}},\"product_brand\":{\"brand\":\"Universal Home Video\",\"manufacturer_brand\":\"Universal Home Video\",\"facet_id\":\"55zki\"},\"item_state\":\"READY_FOR_LAUNCH\",\"specifications\":[],\"attributes\":{\"gift_wrapable\":\"N\",\"has_prop65\":\"N\",\"is_hazmat\":\"N\",\"manufacturing_brand\":\"Universal Home Video\",\"max_order_qty\":10,\"street_date\":\"2011-11-15\",\"media_format\":\"Blu-ray\",\"merch_class\":\"MOVIES\",\"merch_classid\":58,\"merch_subclass\":34,\"return_method\":\"This item can be returned to any Target store or Target.com.\",\"ship_to_restriction\":\"United States Minor Outlying Islands,American Samoa (see also separate entry under AS),Puerto Rico (see also separate entry under PR),Northern Mariana Islands,Virgin Islands, U.S.,APO/FPO,Guam (see also separate entry under GU)\"},\"country_of_origin\":\"US\",\"relationship_type_code\":\"Stand Alone\",\"subscription_eligible\":false,\"ribbons\":[],\"tags\":[],\"ship_to_restriction\":\"This item cannot be shipped to the following locations: United States Minor Outlying Islands, American Samoa, Puerto Rico, Northern Mariana Islands, Virgin Islands, U.S., APO/FPO, Guam\",\"estore_item_status_code\":\"A\",\"is_proposition_65\":false,\"return_policies\":{\"user\":\"Regular Guest\",\"policyDays\":\"30\",\"guestMessage\":\"This item must be returned within 30 days of the ship date. See return policy for details.\"},\"gifting_enabled\":false,\"packaging\":{\"is_retail_ticketed\":false}},\"circle_offers\":{\"universal_offer_exists\":false,\"non_universal_offer_exists\":true}}}";
	private String jsonNoTitle = "{\"product\":{\"deep_red_labels\":{\"total_count\":2,\"labels\":[{\"id\":\"twbl94\",\"name\":\"Movies\",\"type\":\"merchandise type\",\"priority\":0,\"count\":1},{\"id\":\"rv3fdu\",\"name\":\"SA\",\"type\":\"relationship type\",\"priority\":0,\"count\":1}]},\"available_to_promise_network\":{\"product_id\":\"13860428\",\"id_type\":\"TCIN\",\"available_to_promise_quantity\":110.0,\"street_date\":\"2011-11-15T06:00:00.000Z\",\"availability\":\"AVAILABLE\",\"online_available_to_promise_quantity\":110.0,\"stores_available_to_promise_quantity\":0.0,\"availability_status\":\"IN_STOCK\",\"multichannel_options\":[],\"is_infinite_inventory\":false,\"loyalty_availability_status\":\"IN_STOCK\",\"loyalty_purchase_start_date_time\":\"1970-01-01T00:00:00.000Z\",\"is_loyalty_purchase_enabled\":false,\"is_out_of_stock_in_all_store_locations\":false,\"is_out_of_stock_in_all_online_locations\":false},\"item\":{\"tcin\":\"13860428\",\"bundle_components\":{},\"dpci\":\"058-34-0436\",\"upc\":\"025192110306\",\"product_description123\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"downstream_description\":\"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\",\"bullet_description\":[\"<B>Movie Studio:</B> Universal Studios\",\"<B>Movie Genre:</B> Comedy\",\"<B>Run Time (minutes):</B> 119\",\"<B>Software Format:</B> Blu-ray\"]},\"buy_url\":\"https://www.target.com/p/the-big-lebowski-blu-ray/-/A-13860428\",\"enrichment\":{\"images\":[{\"base_url\":\"https://target.scene7.com/is/image/Target/\",\"primary\":\"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\",\"content_labels\":[{\"image_url\":\"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\"}]}],\"sales_classification_nodes\":[{\"node_id\":\"hp0vg\"},{\"node_id\":\"5xswx\"}]},\"return_method\":\"This item can be returned to any Target store or Target.com.\",\"handling\":{},\"recall_compliance\":{\"is_product_recalled\":false},\"tax_category\":{\"tax_class\":\"G\",\"tax_code_id\":99999,\"tax_code\":\"99999\"},\"display_option\":{\"is_size_chart\":false},\"fulfillment\":{\"is_po_box_prohibited\":true,\"po_box_prohibited_message\":\"We regret that this item cannot be shipped to PO Boxes.\",\"box_percent_filled_by_volume\":0.27,\"box_percent_filled_by_weight\":0.43,\"box_percent_filled_display\":0.43},\"package_dimensions\":{\"weight\":\"0.18\",\"weight_unit_of_measure\":\"POUND\",\"width\":\"5.33\",\"depth\":\"6.65\",\"height\":\"0.46\",\"dimension_unit_of_measure\":\"INCH\"},\"environmental_segmentation\":{\"is_hazardous_material\":false,\"has_lead_disclosure\":false},\"manufacturer\":{},\"product_vendors\":[{\"id\":\"1984811\",\"manufacturer_style\":\"025192110306\",\"vendor_name\":\"Ingram Entertainment\"},{\"id\":\"4667999\",\"manufacturer_style\":\"61119422\",\"vendor_name\":\"UNIVERSAL HOME VIDEO\"},{\"id\":\"1979650\",\"manufacturer_style\":\"61119422\",\"vendor_name\":\"Universal Home Ent PFS\"}],\"product_classification\":{\"product_type\":\"542\",\"product_type_name\":\"ELECTRONICS\",\"item_type_name\":\"Movies\",\"item_type\":{\"category_type\":\"Item Type: MMBV\",\"type\":300752,\"name\":\"movies\"}},\"product_brand\":{\"brand\":\"Universal Home Video\",\"manufacturer_brand\":\"Universal Home Video\",\"facet_id\":\"55zki\"},\"item_state\":\"READY_FOR_LAUNCH\",\"specifications\":[],\"attributes\":{\"gift_wrapable\":\"N\",\"has_prop65\":\"N\",\"is_hazmat\":\"N\",\"manufacturing_brand\":\"Universal Home Video\",\"max_order_qty\":10,\"street_date\":\"2011-11-15\",\"media_format\":\"Blu-ray\",\"merch_class\":\"MOVIES\",\"merch_classid\":58,\"merch_subclass\":34,\"return_method\":\"This item can be returned to any Target store or Target.com.\",\"ship_to_restriction\":\"United States Minor Outlying Islands,American Samoa (see also separate entry under AS),Puerto Rico (see also separate entry under PR),Northern Mariana Islands,Virgin Islands, U.S.,APO/FPO,Guam (see also separate entry under GU)\"},\"country_of_origin\":\"US\",\"relationship_type_code\":\"Stand Alone\",\"subscription_eligible\":false,\"ribbons\":[],\"tags\":[],\"ship_to_restriction\":\"This item cannot be shipped to the following locations: United States Minor Outlying Islands, American Samoa, Puerto Rico, Northern Mariana Islands, Virgin Islands, U.S., APO/FPO, Guam\",\"estore_item_status_code\":\"A\",\"is_proposition_65\":false,\"return_policies\":{\"user\":\"Regular Guest\",\"policyDays\":\"30\",\"guestMessage\":\"This item must be returned within 30 days of the ship date. See return policy for details.\"},\"gifting_enabled\":false,\"packaging\":{\"is_retail_ticketed\":false}},\"circle_offers\":{\"universal_offer_exists\":false,\"non_universal_offer_exists\":true}}}";
	
	/**
	 * Method for prerequisite conditions
	 * 
	 */
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testGetProductDetails() throws ServiceException {
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(json,HttpStatus.ACCEPTED);
		Mockito.when(restTemplate.getForEntity(Mockito.any(String.class), Mockito.eq(String.class))).thenReturn(responseEntity);
		assertEquals("The Big Lebowski (Blu-ray)", productServiceImpl.getProductDetails(1234).getName());
	}
	
	
	@Test
	public void testGetProductDetailsNOProductDesc() throws ServiceException {
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(jsonNoTitle,HttpStatus.ACCEPTED);
		Mockito.when(restTemplate.getForEntity(Mockito.any(String.class), Mockito.eq(String.class))).thenReturn(responseEntity);
		assertEquals("No title information Available in the Product Details Response for 1234",
				productServiceImpl.getProductDetails(1234).getMessage());
	}
	
	
	@Test(expected = ServiceException.class)
	public void testGetProductDetailsHHTPClientException() throws ServiceException {
		
		HttpClientErrorException http = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		Mockito.doThrow(http).when(restTemplate).getForEntity(Mockito.any(String.class), Mockito.eq(String.class));
		productServiceImpl.getProductDetails(1234);
	}
	
	
	@Test
	public void testGetProductDetailsHHTPClientException404() throws ServiceException {
		
		HttpClientErrorException http = new HttpClientErrorException(HttpStatus.NOT_FOUND);
		Mockito.doThrow(http).when(restTemplate).getForEntity(Mockito.any(String.class), Mockito.eq(String.class));
		assertEquals("No information Available in the Product Details Response for 1234",productServiceImpl.getProductDetails(1234).getMessage());
	}
	
	
	@Test
	public void testGetProductDetailsNullName() throws ServiceException {
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>((String)null,HttpStatus.ACCEPTED);
		Mockito.when(restTemplate.getForEntity(Mockito.any(String.class), Mockito.eq(String.class))).thenReturn(responseEntity);
		assertEquals("No information Available in the Product Details Response for 1234",productServiceImpl.getProductDetails(1234).getMessage());
	}
	
	
	@Test
	public void getProductCurrentPriceNotFound() throws ServiceException {
		
		
		HttpClientErrorException http = new HttpClientErrorException(HttpStatus.NOT_FOUND);
		Mockito.doThrow(http).when(restTemplate).getForEntity(Mockito.any(String.class), Mockito.eq(String.class));
		assertEquals(null,productServiceImpl.getProductCurrentPrice(1234));
	}
	
	
	@Test(expected = ServiceException.class)
	public void getProductCurrentPriceException() throws ServiceException {
		
		
		HttpClientErrorException http = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		Mockito.doThrow(http).when(restTemplate).getForEntity(Mockito.any(String.class), Mockito.eq(String.class));
		productServiceImpl.getProductCurrentPrice(1234);
	}
	
	@Test
	public void getProductCurrentPrice() throws ServiceException {
		
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("price",HttpStatus.ACCEPTED);
		Mockito.when(restTemplate.getForEntity(Mockito.any(String.class), Mockito.eq(String.class))).thenReturn(responseEntity);
		assertEquals("price",productServiceImpl.getProductCurrentPrice(1234));
	}
	
	@Test
	public void testUpdateProductDetails() throws ServiceException {
		
		RetailResponse retailResponse = new RetailResponse();
		retailResponse.setId(12343);
		retailResponse.setName("Retail product");
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue(1234.90);
		retailResponse.setCurrent_price(currentPrice);
		Mockito.when(productRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(new Product()));
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
		assertEquals("Retail product", productServiceImpl.updateProductDetails(retailResponse).getName());
	}
	
	
	@Test
	public void testUpdateProductDetailsPresent() throws ServiceException {
		
		RetailResponse retailResponse = new RetailResponse();
		retailResponse.setId(12343);
		retailResponse.setName("Retail product");
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue(1234.90);
		currentPrice.setCurrency_code("USD");
		retailResponse.setCurrent_price(currentPrice);
		Optional<Product> product = Optional.ofNullable(null);
		Mockito.when(productRepository.findById(Mockito.any(Integer.class))).thenReturn(product);
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
		assertEquals("Retail product", productServiceImpl.updateProductDetails(retailResponse).getName());
	}
	
	@Test
	public void testCreateProductDetails() throws ServiceException {
		
		RetailResponse retailResponse = new RetailResponse();
		retailResponse.setId(12343);
		retailResponse.setName("Retail product");
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue(1234.90);
		retailResponse.setCurrent_price(currentPrice);
		Mockito.when(productRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(new Product()));
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
		assertEquals("Product already exists in DB.", productServiceImpl.createProductDetails(retailResponse).getMessage());
	}
	
	@Test
	public void testCreateProductDetailsPresent() throws ServiceException {
		
		RetailResponse retailResponse = new RetailResponse();
		retailResponse.setId(12343);
		retailResponse.setName("Retail product");
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue(1234.90);
		currentPrice.setCurrency_code("USD");
		retailResponse.setCurrent_price(currentPrice);
		Optional<Product> product = Optional.ofNullable(null);
		Mockito.when(productRepository.findById(Mockito.any(Integer.class))).thenReturn(product);
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
		assertEquals("Product created in DB.", productServiceImpl.createProductDetails(retailResponse).getMessage());
	}
	
	
	@Test
	public void testSearchProductDetailsPresent() throws ServiceException {
		
		RetailResponse retailResponse = new RetailResponse();
		retailResponse.setId(12343);
		retailResponse.setName("Retail product");
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue(1234.90);
		currentPrice.setCurrency_code("USD");
		retailResponse.setCurrent_price(currentPrice);
		Product product = new Product();
		product.setId(1234);
		product.setCurrency(Currency.AUD);
		product.setName("My Retails");
		product.setPrice(123.90);
		product.setRowCreated(new Date());
		product.setRowUpdated(new Date());
		List<Product> lst = new ArrayList<Product>();
		lst.add(product);
		Mockito.when(productRepository.findByPriceBetween(Mockito.any(Double.class),Mockito.any(Double.class))).thenReturn(lst);
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
		assertEquals(1, productServiceImpl.searchProductDetails(123.89,129.89).size());
	}
	
	@Test
	public void testSearchProductDetailsEmpty() throws ServiceException {
		
		RetailResponse retailResponse = new RetailResponse();
		retailResponse.setId(12343);
		retailResponse.setName("Retail product");
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue(1234.90);
		currentPrice.setCurrency_code("USD");
		retailResponse.setCurrent_price(currentPrice);
		List<Product> lst = new ArrayList<Product>();
		Mockito.when(productRepository.findByPriceBetween(Mockito.any(Double.class),Mockito.any(Double.class))).thenReturn(lst);
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
		assertEquals("No Products exists in DB for the prices between 123.89 and 129.89", productServiceImpl.searchProductDetails(123.89,129.89).get(0).getMessage());
	}
	
}


