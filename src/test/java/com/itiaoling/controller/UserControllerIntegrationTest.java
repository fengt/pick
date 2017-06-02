package com.itiaoling.controller;


import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.itiaoling.Application;
import com.itiaoling.model.User;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserControllerIntegrationTest {

	private static final String REST_SERVICE_URI = "http://localhost:10001/users";
	private static final String AUTH_SERVER_URI = "http://localhost:10001/oauth/token";
	private static final String PASSWORD_GRANT = "?grant_type=password&username=fengt&password=12345678";
	private static final String ACCESS_TOKEN = "?access_token=";
	
	private String token;
	
	@Autowired
	private RestTemplate template;
	
	
	@Before
	public void setUp(){
		this.token = getAccessToken();
		assertNotNull(token);
	}
	
	
	
	
	
	// =========================================== Get All Users ==========================================
	
	@Test
	public void test_get_all_success() {
		ResponseEntity<User[]> response = template.getForEntity(REST_SERVICE_URI + ACCESS_TOKEN + token, User[].class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		validateCORSHttpHeaders(response.getHeaders());
	}
	
	
	
	// =========================================== CORS Headers ===========================================
	
	public void validateCORSHttpHeaders(HttpHeaders headers) {
		assertThat(headers.getAccessControlAllowOrigin(), is("*"));
		assertThat(headers.getAccessControlAllowHeaders(), hasItem("Authorization"));
		assertThat(headers.getAccessControlMaxAge(), is(3600L));
		assertThat(headers.getAccessControlAllowMethods(), hasItems(
				HttpMethod.GET,
				HttpMethod.POST,
				HttpMethod.OPTIONS,
				HttpMethod.PUT,
				HttpMethod.DELETE));
	}
	
	
	
	
	
	
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
	
	private HttpHeaders getHeadersWithClientCredentials() {
		String plainClientCredentials = "client:secret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
		
		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}
	
	private String getAccessToken(){
		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<Object> response = template.exchange(
				AUTH_SERVER_URI + PASSWORD_GRANT, 
				HttpMethod.POST, request, Object.class);
		
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) response.getBody();
		if(map != null){
			return (String) map.get("access_token");
		} else {
			return null;
		}
	}
}
