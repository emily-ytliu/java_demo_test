package com.example.java_demo_test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiTest {

	@SuppressWarnings("unchecked")
	@Test
	public void getApiTest() throws JsonMappingException, JsonProcessingException {
		String targetUrl = "https://opendata.hccg.gov.tw/API/v3/Rest/OpenData/704FC0B2EE7500E4?take=10&skip=0";
		//透過RestTemplate連接外部的API
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resString = restTemplate.getForEntity(targetUrl, String.class);  //String.class 是要轉成的型態
		
		System.out.println(resString.getStatusCode());
		System.out.println(resString.getStatusCodeValue());
		String responseString = resString.getBody();
		System.out.println(responseString);
		
		ObjectMapper mapper = new ObjectMapper();
		//把responseString的字串，轉成List
		List<Map<String, Object>> resList = mapper.readValue(responseString, List.class);  //List.class 是要轉成的型態
		//取出字串里的key和value
		for (Map<String, Object> item : resList) {
			for (Entry<String, Object> map : item.entrySet()) {
				System.out.println("key: " + map.getKey());
				System.out.println("value: " + (String)map.getValue());
			}
		}
	}
	
	@Test
	public void postApiTest() {
		String targetUrl = "http://172.20.10.3:8080/register";
		Map<String, String> bodyMap = new HashMap<>();
		bodyMap.put("account", "golden retriever");
		bodyMap.put("pwd", "AA123");
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String reqBodyStr = mapper.writeValueAsString(bodyMap);  //bodyMap轉成String
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> requestBody = new HttpEntity<String>(reqBodyStr, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(targetUrl, requestBody, String.class);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
