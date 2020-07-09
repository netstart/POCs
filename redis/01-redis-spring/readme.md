Demontra como usar o Redis como cache
Neste caso o Cache também é conhecido em outros ecosistems como memoize


docker run -it \
    --name redis \
    -p 6379:6379 \
    redis:5.0.3
    
    

curl -s http://localhost:8080/company \
 -H "Content-Type: application/json" \
 -d '{"id": "1010", "name": "Company 1"}'
 
curl -s http://localhost:8080/company \
 -H "Content-Type: application/json" \
 -d '{"id": "1020", "name": "Company 2"}'

curl -s http://localhost:8080/company \
 -H "Content-Type: application/json" \
 -d '{"id": "1030", "name": "Company 3"}'

curl -s http://localhost:8080/company \
 -H "Content-Type: application/json" \
 -d '{"id": "1040", "name": "Company 4"}'
 
curl -s http://localhost:8080/company/

curl -s -X PUT http://localhost:8080/company \
 -H "Content-Type: application/json" \
 -d '{"id": "1010", "name": "Company 1010"}'
 
 curl -s -X PUT http://localhost:8080/company \
 -H "Content-Type: application/json" \
 -d '{"id": "1030", "name": "Company 10301030"}'
 
curl -s http://localhost:8080/company/composite/1030
 
curl -s http://localhost:8080/company/1010

curl -s -X DELETE http://localhost:8080/company/1040 

