# Social Score Service

A spring Boot REST application.


## Persistence

For this test I use an embeded mongodb as DB, this can be replace with Elastic search. 
I used it just to save time since i'm using it in current work


### Some Decision  with  DB

For the export functionality i used the aggregation ability of DB (Same can be done with Elastic search)
rather than doing it in the application.

Persistence layer has it's on Database object for saving information to DB, this approach is taken to save an enriched version of Social score to DB

Also URL is treated as unique in DB 


## Logging
Choose to stick with default logging of Spring boot (logback).



## Samples

### To Create

````bash
curl -X POST \
  http://localhost:8080/save \
  -H 'Content-Type: application/json' \
  -d '{
	"url":"http://www.d.com/search?query=somethingd",
	"score":10
}'
````

### To Search

````bash
curl -X GET \
  'http://localhost:8080/search?key=id&value=5d3333bd38ab1f0ac85eb7ec'
````
### To Delete Using id

````
curl -X DELETE \
  'http://localhost:8080/delete?key=id&value=5d3333bd38ab1f0ac85eb7ec' 
````

### To Delete using URL

````
curl -X DELETE \
  'http://localhost:8080/delete?key=id&value=5d3333bd38ab1f0ac85eb7ec' 
````

### To Update using id

````bash
curl -X PATCH \
  http://localhost:8080/save \
  -H 'Content-Type: application/json' \
  -d '{
    "key":"id",
	"value":"5d3333bd38ab1f0ac85eb7ec",
	"updatedScore":10
}'
````

### To Update using url

````bash
curl -X PATCH \
  http://localhost:8080/save \
  -H 'Content-Type: application/json' \
  -d '{
    "key":"url",
	"value":"http://www.d.com/search?query=something",
	"updatedScore":10
}'
````

## Missing Following 

- Security implementation around REST API.
- REST docs tests
- Swagger API implementation
- Cloud contract Tests
- End to end integration test
- Controller Adviser for validation
- Errors should be model to proper Error model 