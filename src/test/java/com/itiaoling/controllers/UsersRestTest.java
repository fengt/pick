package com.itiaoling.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.itiaoling.Application;
import com.itiaoling.controller.HomeController;
import com.itiaoling.controller.UserController;
import com.itiaoling.model.User;
import com.itiaoling.service.UsersService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UsersRestTest {
	
	private MockMvc mvc;
	
	@Mock
	private UsersService usersService;
	
	@InjectMocks
    private UserController userController;
	
	@InjectMocks
    private HomeController homeController;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(
				homeController, 
				userController
				).build();
	}
	
	
	@Test
	public void getHome() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Hello World")));
	}

	
	// =========================================== Get All Users ==========================================
	
	@Test
	public void test_get_all_users() throws Exception {
		List<User> users = Arrays.asList(
				new User(1L, "John", "John@126.com", "12345678"),
				new User(2L, "Tom", "Tom@126.com", "1233456"));
		
		when(usersService.findAllUsers()).thenReturn(users);
		
		mvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("John")))
			.andExpect(jsonPath("$[1].email", is("Tom@126.com")));
		
		verify(usersService, times(1)).findAllUsers();
		verifyNoMoreInteractions(usersService);
	}
	
	
}
