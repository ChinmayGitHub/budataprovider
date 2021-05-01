package com.howtodoinjava.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.demo.model.BUdata;
import com.howtodoinjava.demo.model.Employee;

@RestController
public class EmployeeController {
	
	@RequestMapping("/")
    public List<Employee> getEmployees() 
    {
		List<Employee> employeesList = new ArrayList<Employee>();
		employeesList.add(new Employee(1,"bakla","gupdta","howtodoinjava@gmail.com"));
		return employeesList;
    }
	
	@RequestMapping("/getPythondatafeederHOST")
    public String getPythondatafeederURL() 
    {
		String pythondatafeeder_host = System.getenv("pythondatafeeder_host");

		return pythondatafeeder_host;
    }
	
	@RequestMapping("/BUdata")
    public BUdata getBUdata() throws IOException 
    {
		String pythondatafeeder_url = System.getenv("pythondatafeeder_host");
		if (pythondatafeeder_url == null || pythondatafeeder_url.isEmpty()) {
			pythondatafeeder_url = "pythondatafeeder";
		}
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response
		  = restTemplate.getForEntity("http://"+pythondatafeeder_url+":5000/getDivision" , String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode division = root.path("division");
		
		return new BUdata(1,"IBM" + "-" + division.asText());
    }

}
