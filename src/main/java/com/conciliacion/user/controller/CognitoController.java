package com.conciliacion.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.conciliacion.user.service.CogniteService;

@RestController
@RequestMapping("/v1/confirm-signup")
public class CognitoController {
	@Autowired
	public CogniteService service;
	
	@GetMapping
	public ResponseEntity<String> verify(@RequestParam String email, @RequestParam String code){

		String reponse = service.confirmSignUp(email, code);
		
		return ResponseEntity.ok(reponse);		
	}
}
