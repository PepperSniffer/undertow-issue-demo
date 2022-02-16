<h1>This is a demo project that shows a bug in the undertow servlet when called by an api-gateway (using X-Forwarded headers) and dealing with webflux or Async responses</h1>



There are two endpoints in this api
* sync : will work as expected
* async : will return a 404 error after 30 seconds such as :
	 *  {"timestamp":"2022-02-15T14:51:51.363+00:00","status":404,"error":"Not Found","path":"/api/rest/{ \"testAsync\": \"Hello foo\"}"}

steps to reproduce
* clone this repo
* mvn spring-boot:run with a java 11 JVM
* curl -XGET localhost:8080/api/rest/demo/async/foo -H "Host: localhost:8080" -H "Connection: keep-alive" -H "X-Forwarded-For: 192.168.0.1" -H "X-Forwarded-Proto: http" -H "X-Forwarded-Host: my-fakegateway.com" -H "X-Forwarded-Port: 80" -H "X-Forwarded-Prefix: /custompath/demo/" -H "X-Forwarded-Path: /custompath/demo/sync/foo" -H "X-Real-IP: 192.168.0.1"




