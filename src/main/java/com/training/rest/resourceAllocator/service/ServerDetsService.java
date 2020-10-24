package com.training.rest.resourceAllocator.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

public interface ServerDetsService {

	public HashMap<String,HashMap<String,Float>> saveAll(HashMap<String,HashMap<String,Float>> serverDetails);
	
	public HashMap<String, HashMap<String, Float>> getAll();
}
