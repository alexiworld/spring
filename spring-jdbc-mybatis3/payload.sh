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


curl -H "Content-type: application/json" localhost:8080/rest/v1/todo -d @todo1.json
curl -H "Content-type: application/json" localhost:8080/rest/v1/todo -d @todo1.json
curl -H "Content-type: application/json" localhost:8080/rest/v1/todo/
curl -H "Content-type: application/json" localhost:8080/rest/v1/todo/count
curl -H "Content-type: application/json" localhost:8080/rest/v1/todo/1
curl -H "Content-type: application/json" localhost:8080/rest/v1/todo/2
curl -X PUT -H "Content-type: application/json" localhost:8080/rest/v1/todo/2 -d @todo2.json
curl -X DELETE -H "Content-type: application/json" localhost:8080/rest/v1/todo/2
