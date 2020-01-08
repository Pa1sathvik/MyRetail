package com.target.myretail.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.response.RetailResponse;
import com.target.myretail.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
@ContextConfiguration(classes = ProductController.class)
@ComponentScan(basePackages = "com.target")
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
	}

	@Test
	public void testgetProductData() throws Exception {

		Mockito.when(productService.getProductDetails(Mockito.any(Integer.class))).thenReturn(new RetailResponse());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/{id]", 1298)
				.accept(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"current_price\": {\r\n" + "        \"currency_code\": \"USD\",\r\n"
						+ "        \"value\": \"1234.78\"\r\n" + "    },\r\n" + "    \"id\": 1,\r\n"
						+ "    \"name\": \"The Big Lebowski (Blu-ray)\"\r\n" + "}");

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testupdateProductData() throws Exception {

		ObjectMapper obj = new ObjectMapper();
		Mockito.when(productService.updateProductDetails(Mockito.any(RetailResponse.class)))
				.thenReturn(new RetailResponse());

		mockMvc.perform(MockMvcRequestBuilders.post("/products").contentType(MediaType.APPLICATION_JSON)
				.content(obj.writeValueAsString(new RetailResponse())))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print());

	}
}
