package com.itiaoling.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itiaoling.exception.ErrorEntity;
import com.itiaoling.exception.ErrorResponseEntity;
import com.itiaoling.model.User;
import com.itiaoling.service.UsersService;


@RestController
@RequestMapping("/users")
public class UserController {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    UsersService usersService;
	
	
	
	
	//------------------------------------Retrieve All Users------------------------------------
	
	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping(value = {"/", ""})
	public ResponseEntity<List<User>> listAllUsers(){
		List<User> users = usersService.findAllUsers();
		if(users.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	
	
	
	//------------------------------------Retrieve Single User------------------------------------
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") long id){
		LOGGER.info("Fetching User with id {}", id);
		
		User user = usersService.findById(id);
		if(user == null){
			
			LOGGER.error("User with id {} not found.", id);
			
			return new ResponseEntity<>(
					new ErrorEntity("user", "id", "User with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
	
	//------------------------------------Create a User------------------------------------
	
	@PostMapping(value = {"/", ""})
	public ResponseEntity<?> createUser(@Valid @RequestBody User user, Errors errors){
		LOGGER.info("Creating User : {}", user);
		
		ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity();
		
		if(errors.hasErrors()){
			errorResponseEntity.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
			errorResponseEntity.setMessage("Validation Failed");
			
			errorResponseEntity.setErrors(errors.getFieldErrors()
					.stream().map(x -> new ErrorEntity(x.getObjectName(), x.getField(), x.getDefaultMessage()))
					.collect(Collectors.toList()));
			
			return ResponseEntity.unprocessableEntity().body(errorResponseEntity);
		}
		
		if(usersService.isUserExists(user)){
			LOGGER.error("Unable to create. A user with name {} already exists", user.getName());
			//TODO just omit. Actually it also need to verify email unique
			
			return new ResponseEntity<>(
					new ErrorEntity("user", "name", "Unable to create. A user with name " + user.getName() + " already exists"),
					HttpStatus.CONFLICT);
		}
		
		BCryptPasswordEncoder encodePW = new BCryptPasswordEncoder();
		user.setPassword(encodePW.encode(user.getPassword()));
		usersService.saveUser(user);
		
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	
	
	
	//------------------------------------Update a User------------------------------------
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user, Errors errors){
		LOGGER.info("Updating User with id {}", id);
		
		if(errors.hasErrors()){
			
			return ResponseEntity.unprocessableEntity().body(
					new ErrorResponseEntity(
							HttpStatus.UNPROCESSABLE_ENTITY.value(),
							"Validation Failed",
							errors.getFieldErrors().stream()
								.map(x -> new ErrorEntity(x.getObjectName(), x.getField(), x.getDefaultMessage()))
								.collect(Collectors.toList())
							));
		}
		
		
		User currentUser = usersService.findById(id);
		
		if(currentUser == null){
			LOGGER.error("Unable to update. User with id {} not found.", id);
			
			return new ResponseEntity<>(
					new ErrorEntity("user", "id", "Unable to update. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		
		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		BCryptPasswordEncoder encodePW = new BCryptPasswordEncoder();
		currentUser.setPassword(encodePW.encode(user.getPassword()));
		
		usersService.updateUser(currentUser);
		
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	
	
	
	//------------------------------------Delete a User------------------------------------
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
		LOGGER.info("Fetching & Deleting User with id {}", id);
		
		User user = usersService.findById(id);
		if(user == null){
			LOGGER.error("Unable to delete. User with id {} not found.", id);
			
			return new ResponseEntity<>(
					new ErrorEntity("user", "id", "Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		usersService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
	//------------------------------------Delete All Users------------------------------------
	
	@DeleteMapping(value = {"/", ""})
	public ResponseEntity<User> deleteAllUsers(){
		LOGGER.info("Deleting All Users");
		
		usersService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	
}
