package com.conciliacion.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.conciliacion.user.entity.User;
import com.conciliacion.user.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(
			@RequestParam(value="username", required = false, defaultValue = "") String username,
			@RequestParam(value="offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit",required = false,defaultValue = "5") int pageSize
			){
		
		Pageable page=PageRequest.of(pageNumber,pageSize);
		List<User> users;
		if(username == null) {
			users=service.findAll(page);
		}else {
			users=service.findByUserName(username, page);
		}
		
		if(users.size() > 0) {
			return ResponseEntity.ok(users);	
		}else {
			return ResponseEntity.noContent().build();
		}			
	}
	
	@PutMapping()
	public ResponseEntity<User> update(@RequestBody User user){		
		User registro=service.update(user);
		return ResponseEntity.ok(registro);
	}
	
	@PostMapping()
	public ResponseEntity<User> create(@RequestBody User user){
		Pageable page=PageRequest.of(0,5);
		List<User> users;
		users=service.findByUserName(user.getUserName(), page);
		
		if(users.size() == 0) {
			User registro=service.create(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(registro);	
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}			
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<User> delete(@PathVariable("id") int id){
		service.delete(id);
		return ResponseEntity.ok(null);
	}
}
