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

###Errors/Validations: Appropriate error messages are provided after validating the data. More information is available in the below sections. The client application can use the message in the response to display the same to the user appropriately.
	
	
