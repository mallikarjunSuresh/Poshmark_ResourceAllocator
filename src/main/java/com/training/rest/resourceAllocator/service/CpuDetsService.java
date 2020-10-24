package com.training.rest.resourceAllocator.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

public interface CpuDetsService {

	public HashMap<String,Integer> findAll();
	
	public HashMap<String,Integer> SaveAll(HashMap<String,Integer> cpuDetsHash);
	
}
