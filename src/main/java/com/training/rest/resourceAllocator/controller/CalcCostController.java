package com.training.rest.resourceAllocator.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.training.rest.resourceAllocator.Optmizer;
import com.training.rest.resourceAllocator.Result;

@RestController
public class CalcCostController {
	
	@Autowired
	public Optmizer optmizer;
		
	
	@GetMapping(path = "/ServerCost/hours/{hours}/cpus/{cpus}/price/{price}")
	public ArrayList<Result> getCost(@PathVariable int hours, @PathVariable int cpus 
			,@PathVariable float price) 
	{		
		return optmizer.startOptmize(cpus,price,hours);
	}	
	
	
	
	
}

	
	
