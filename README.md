# MyRetailRestService
MyRetailRestService is a Restful web service for Products API which gets product from multiple sources and updates price in the backend Table.

### Prerequisites
* Java version 1.8
* Maven  -- Wrapper is avaialable 
* Mongo DB which runs on cloud


### Running the application using Maven

We can run the application by downloading into local and run the below command. By default the application runs on port 8080. We can change by changing the port in application.properties file.

```
./mvnw clean package
./mvnw spring-boot:run
```

### Endpoints
* http://localhost:8080/v1/products/{id} -> Get : To get the product details based on product Id
* http://localhost:8080/v1/products/{id} -> Put: To update price of the product

#### CURL
* curl http://localhost:8080/v1/products/13860427
* curl -X PUT -H "Content-Type: application/json" -d '{"id":13860499,"name":"Conan the Barbarian (dvd_video)","current_price":{"value":0.01,"currency_code":"USD"}}' http://localhost:8080/v1/products/13860499

### Sample Products:
* 13860427,13860428,13860429 

### Sample Json 

```
{
    "id": 13860499,
    "name": "Conan the Barbarian (dvd_video)",
    "current_price": {
        "value": 0.01,
        "currency_code": "USD"
    }
}
```
