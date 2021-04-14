package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.response.ErrorResponse;

@RestController
@RequestMapping("/error")
public class ErrorController {

	@GetMapping("/401")
	public ResponseEntity<?> getUnauthorized() {
		ErrorResponse response = new ErrorResponse();
		response.setMessage("Invalid token or expired");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
	@GetMapping(value = "/403")
	public ResponseEntity<?> getAccessdenied() {
		ErrorResponse response = new ErrorResponse();
		response.setMessage("Access Denied");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
}
