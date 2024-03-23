curl -H "Content-type: application/json" localhost:8080/rest/v1/vehicle -d @payload1.json
curl -H "Content-type: application/json" localhost:8080/rest/v1/vehicle -d @payload2.json
curl -H "Content-type: application/json" localhost:8080/rest/v1/vehicle -d @payload3.json
curl localhost:8080/rest/v1/vehicle/
curl localhost:8080/rest/v1/vehicle/1
curl localhost:8080/rest/v1/vehicle/2
curl localhost:8080/rest/v1/vehicle/3
curl localhost:8080/rest/v1/vehicle/year/2023
curl localhost:8080/rest/v1/vehicle/vin/vin123
curl localhost:8080/rest/v1/vehicle/vin/vin456
curl localhost:8080/rest/v1/vehicle/vin/vin789
