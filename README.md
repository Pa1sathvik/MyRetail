**#MyRetail REST API**

MyRetail RESTful service provides the client application ability to:
```
  
  1. Retrieve Product and Price information by Product Id.
	
  2. Send request to modify the price information in the database.
	
  3. Update / Create product information details in database.
  
  4. Search for Products in repository between prices.  
  
```

**## Basic Authentication for security to REST API**:

All the REST API's in My retals project are secured with basic authentication.

Credential details :-- Username ===> "user"
                       Password ===> "password"


	
 **##Get Product Information**:
	
###Input: The client application does a GET request at the path "/products/{id}" for a product with the basic authentication credentials.

###Internal Working: When the API receives the request, it sends a request to "redsky.target.com" and retrieves the product information. This product information doesn't contain price that is needed by the user. The price is retrieved from a data store. The price information is now combined with the required product information to provide only the required product information to the user.

###Output: For a product with product id '13860428', the sample JSON output is as shown below

{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}

###Errors/Validations: All the validation and error message are handled porperly.Application can display messages to the user.

**## Update Product Price in the datastore**:

###Input: The user/client application can do a PUT request with input similar to the response received in GET and should be able to modify the price in the datastore. The request is done at the same path "/products/{id}". Authorization should be added in request header with basic security credentials.

####Sample Input: JSON Body - {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 15.67,"currency_code":"USD"}}

###Internal Working: When the API receives PUT request, it does some validations to see if the product is available. If it is, it ensures that the id in the URL and the JSON body is similar and if that looks same, the price for the product is modified in the data store.

###Output: Success message is returned if the price modification is done.

###Errors/Validations: All the validation and error message are handled porperly.Application can display messages to the user.

**## Update Product Price in the datastore**:

###Input: The user/client application can do a POST request with input similar to the response received in GET and should be able to create the product details in the datastore if product is not present. The request is done at the same path "/products/{id}". Authorization should be added in request header with basic security credentials.

####Sample Input: JSON Body - {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 15.67,"currency_code":"USD"}}

###Internal Working: When the API receives PUT request, it does some validations to see if the product is available. If it is, it ensures that the id in the URL and the JSON body is similar and if that looks same, the price for the product is modified in the data store.

###Output: Success message is returned if the product is created in DB.

###Errors/Validations: All the validation and error message are handled porperly.Application can display messages to the user.


	
	
