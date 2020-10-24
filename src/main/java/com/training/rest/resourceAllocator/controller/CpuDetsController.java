package com.training.rest.resourceAllocator.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.rest.resourceAllocator.service.CpuDetsService;

@RestController
public class CpuDetsController {

	@Autowired
	CpuDetsService cpuDetsService;
	
	@PostMapping(path = "/CpuDets")
	public HashMap<String,Integer> resourceInfo(@RequestBody HashMap<String,Integer> cpuDetails) 
	{
		return cpuDetsService.SaveAll(cpuDetails);
	}
	
	@GetMapping(path = "/CpuDets")
	public HashMap<String,Integer> resourceInfo() 
	{
		
		return cpuDetsService.findAll();
	}
}
