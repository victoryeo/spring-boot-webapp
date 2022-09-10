## demo webapp
written in java spring boot

## curl command
to test books CRUD
```
curl -X GET localhost:8081/api/books
curl -H "Content-Type: application/json" -X POST -d '{"title":"test","author":"me"}' localhost:8081/api/books/
curl -H "Content-Type: application/json" -X POST -d '{"title":"test","author":"me"}' localhost:8081/api/books/test
```
