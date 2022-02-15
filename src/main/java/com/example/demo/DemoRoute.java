package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/demo")
public class DemoRoute {
	/**
	 * When calling with X-Forwarded headers
	 *
	 *  sync : will work as expected
	 *  async : will return a 404 error after 30 seconds such as :
	 *  {"timestamp":"2022-02-15T14:51:51.363+00:00","status":404,"error":"Not Found","path":"/api/rest/{ \"testAsync\": \"Hello foo\"}"}
	 *
	 *  curl -XGET localhost:8080/api/rest/demo/async/foo -H "Host: localhost:8080" -H "Connection: keep-alive" -H "X-Forwarded-For: 192.168.0.1" -H "X-Forwarded-Proto: http" -H "X-Forwarded-Host: my-fakegateway.com" -H "X-Forwarded-Port: 80" -H "X-Forwarded-Prefix: /custompath/demo/" -H "X-Forwarded-Path: /custompath/demo/sync/foo" -H "X-Real-IP: 192.168.0.1"
	 *
	 */

	/**
	 * This represents the spring boot route that returns a sync response
	 * @return a ResponseEntity of String
	 */
	@RequestMapping(value = "/sync/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> callSync(@PathVariable String name) {
		return ResponseEntity.ok("{ \"testNoAsync\": \"Hello " + name + "\"}");
	}

	/**
	 * This represents the spring boot route that return an async response
	 *
	 * will have the same behavior if returning a CompletableFuture instead of a Mono
	 * @return a ResponseEntity of String
	 */
	@RequestMapping(value = "/async/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompletableFuture<String>> callAsync(@PathVariable String name) {
		CompletableFuture<String> of = CompletableFuture.completedFuture("{ \"testAsync\": \"Hello " + name + "\"}");
		return ResponseEntity.ok(of);
	}
}
