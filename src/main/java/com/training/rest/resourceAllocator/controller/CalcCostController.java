package com.training.rest.resourceAllocator.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.training.rest.resourceAllocator.component.OptmizerFacade;

@RestController
public class CalcCostController {
	
	@Autowired
	public OptmizerFacade optmizerFacade;
		
	
	@GetMapping(path = "/ServerCost/hours/{hours}/cpus/{cpus}/price/{price}")
	public ArrayList<HashMap<String,Object>> getCost(@PathVariable int hours, @PathVariable int cpus 
			,@PathVariable float price) 
	{		
	
		return optmizerFacade.startOptmize(cpus,price,hours);
	}	
	
	
	
	
}

	
	
