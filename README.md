# autohaus

### What's this???

This is a first implementation of a platform for selling vehicles. 

### Swagger api documentation

- https://github.com/mlecar/autohaus/blob/main/src/main/resources/swagger.yaml

## How to?
### Step 1 - Setup database
```
/utilities/docker-compose up
```

### Step 2 - Compile it with
```
$ ./mvnw clean install
```

### Step 3 - Start it
```
$ java -jar target/autohaus-1.0-SNAPSHOT.jar
```

### Step 4 - Test it
```
POST /dealers/123/vehicles
Content-type:multipart/form-data
returns: HTTP 204, NO CONTENT

$ curl -v -XPOST -F "file=@base_vehicle_listing.csv" http://localhost:8080/dealers/123/vehicles
```

```
POST /dealers/123/vehicles
Content-type:application/json

$ curl -v -XPOST -H "Content-type:application/json" -d \
'[
{
"code": "a",
"make": "renault",
"model": "megane",
"kW": 132,
"year": 2014,
"color": "red",
"price": 13990
},
{
"code": "b",
"make": "bmw",
"model": "x6",
"kW": 148,
"year": 2015,
"color": "green",
"price": 15410
}
]' \
 http://localhost:8080/dealers/456/vehicles
```

```
GET /vehicles

curl -v -XGET http://localhost:8080/vehicles | python -m json.tool
```

```
GET /vehicles?make=bmw

curl -v -XGET http://localhost:8080/vehicles?make=bmw | python -m json.tool
```

### Unit Tests
Unit tests are implemented in folder
```
src/test/java
```

It can be executed as:
```
mvn clean install
```

### Integration Tests
Integration tests are implemented in folder
```
src/integration-test/java
```

It can be executed using **integration-tests** profile as example below:
```
mvn clean install -Pintegration-tests
```

### Problems that I found
- /upload_csv/{dealer_id} and /vehicles_listing are not good
- No clear how Provider is informed. Will we receive it in header?
- Tried to use querydsl with spring-data-jdbc to provide a better search, but no success.

### What is missing?
- No validation on search criteria on GET /vehicles
- Search by multiple values for one parameter, like ?make=bmw,audi
- comma separated values are there, but maybe semi-colon is also needed where parsing csv
- 2 steps for uploading files is needed? For example, uploading and then confirming changes
- Provider identification would be useful for tracking updates, metrics
- how big are the files/dataset from dealers?
- prices were considered as integers
- Dealers are not validated
- Should csv processing fail when a column is missing?
- Historical data for uploaded listings? 

### If I had more time
- basic validation for searched parameters
- allow multiple values for one parameter
- semi-colon support

### What is missing for production
- More unit tests
- More Integration tests
- Metrics and monitoring
- Caching
- Password encryption with KMS for example
