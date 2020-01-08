**#MyRetail REST API**

MyRetail RESTful service provides the client application ability to:
```
  
  1. Retrieve Product and Price information by Product Id.
	
  2. Send request to modify the price information in the database.
	
  3. Update / Create product information details in database.
  
  4. Search for Products in given price range.  
  
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

###Output: Success message is returned if the price modification is done along with JSON output shon below

{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}

###Errors/Validations: All the validation and error message are handled porperly.Application can display messages to the user.

**## Create Product  in the datastore**:

###Input: The user/client application can do a POST request with input similar to the response received in GET and should be able to create the product details in the datastore if product is not present. The request is done at the same path "/products/{id}". Authorization should be added in request header with basic security credentials.

####Sample Input: JSON Body - {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 15.67,"currency_code":"USD"}}

###Internal Working: When the API receives POST request, it does some validations to see if the product is available. If product is already available then error message will be sent back , otherwise new product will be created in the repository.

###Output: Success message is returned if the product is created in DB.

###Errors/Validations: All the validation and error message are handled porperly.Application can display messages to the user.


**## Search Products in the datastore in the given price range**:

###Input: The user/client application can do a GET request with queryparams ?lowprice and &highprice.Application then searches for the product with in given price range. The request is done at the same path "products/search?pricelow={low}&pricehigh={high}". Authorization should be added in request header with basic security credentials.

####Sample Input: JSON Body - {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 15.67,"currency_code":"USD"}}

###Internal Working: When the API receives GET request, application will searches for the products in repository with in given price range.

###Output: Details of products with in given price range will be returned.

###Errors/Validations: All the validation and error message are handled porperly.Application can display messages to the user.


**##Technologies Used**:

1. Spring Boot - https://projects.spring.io/spring-boot/
2. OracleDB - https://www.oracle.com/database/
3. Swagger - http://swagger.io/
4. Maven - https://mvnrepository.com/
5. Netflix Hystrix - https://github.com/Netflix/Hystrix 


**##Instructions to Setup**

1. Install OracleDB in your system - https://www.oracle.com/webfolder/technetwork/tutorials/obe/db/12c/r1/Windows_DB_Install_OBE/Installing_Oracle_Db12c_Windows.html

Set the oracle db parameters as mentioned below:-
 ```
 URL : jdbc:oracle:thin:@localhost:1521/localdb
 UserName : iat
 Password : iat
 ```
2. Install Maven - https://maven.apache.org/install.html
3. Clone the code or download from git repository - https://github.com/Pa1sathvik/MyRetail.git
4. Open browser and visit Swagger. http://localhost:8080/swagger-ui.html
5. Swagger documentation explains the expected request and response for GET and PUT requests.
6. Open browser and visit Hystrix dashboard http://localhost:8080/swagger-ui.html for monitoring fault tolerance and circuit breaker pattern of My Retail REST API's
	
	
