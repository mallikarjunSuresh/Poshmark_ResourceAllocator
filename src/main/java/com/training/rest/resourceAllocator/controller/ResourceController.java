package com.training.rest.resourceAllocator.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.rest.resourceAllocator.service.ServerDetsService;


@RestController
public class ResourceController {
		
	@Autowired
	ServerDetsService serverDetService;
	
	@PostMapping(path = "/ResourceInfo/Resource")
	public HashMap<String,HashMap<String,Float>> resourceInfo(@RequestBody HashMap<String,HashMap<String,Float>> serverDetails) 
	{
		return serverDetService.saveAll(serverDetails);
	}
	
	@GetMapping(path = "/ResourceInfo/Resource")
	public HashMap<String,HashMap<String,Float>> resourceInfo() 
	{
		
		return serverDetService.getAll();
	}

}
