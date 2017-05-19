package com.itiaoling.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.itiaoling.models.User;
import com.itiaoling.services.UsersService;



@RestController
@RequestMapping("/api")
public class UserController {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    UsersService usersService;
	
	
	
	
	//------------------------------------Retrieve All Users------------------------------------
	
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers(){
		List<User> users = usersService.findAllUsers();
		if(users.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	
	
	
	//------------------------------------Retrieve Single User------------------------------------
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id){
		LOGGER.info("Fetching User with id {}", id);
		
		User user = usersService.findById(id);
		if(user == null){
			
			LOGGER.error("User with id {} not found.", id);
			return new ResponseEntity<>("User with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
	
	//------------------------------------Create a User------------------------------------
	
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
		LOGGER.info("Creating User : {}", user);
		
		if(usersService.isUserExists(user)){
			LOGGER.error("Unable to create. A user with name {} already exists", user.getName());
			
			return new ResponseEntity<>("Unable to create. A user with name " + user.getName() + " already exists",
					HttpStatus.CONFLICT);
		}
		
		BCryptPasswordEncoder encodePW = new BCryptPasswordEncoder();
		user.setPassword(encodePW.encode(user.getPassword()));
		usersService.saveUser(user);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	
	
	
	//------------------------------------Update a User------------------------------------
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user){
		LOGGER.info("Updating User with id {}", id);
		
		User currentUser = usersService.findById(id);
		
		if(currentUser == null){
			LOGGER.error("Unable to update. User with id {} not found.", id);
			
			return new ResponseEntity<>("Unable to update. User with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		
		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		BCryptPasswordEncoder encodePW = new BCryptPasswordEncoder();
		currentUser.setPassword(encodePW.encode(user.getPassword()));
		
		usersService.updateUser(currentUser);
		
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	
	
	
	//------------------------------------Delete a User------------------------------------
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
		LOGGER.info("Fetching & Deleting User with id {}", id);
		
		User user = usersService.findById(id);
		if(user == null){
			LOGGER.error("Unable to delete. User with id {} not found.", id);
			
			return new ResponseEntity<>("Unable to delete. User with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		usersService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
	//------------------------------------Delete All Users------------------------------------
	
	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers(){
		LOGGER.info("Deleting All Users");
		
		usersService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	
}
