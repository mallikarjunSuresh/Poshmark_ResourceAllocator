package com.training.rest.resourceAllocator.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.rest.resourceAllocator.model.CpuDets;
import com.training.rest.resourceAllocator.repository.CpuDetsRepository;

@Service
public class CpuDetsServiceImp implements CpuDetsService{
	
	public CpuDetsServiceImp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	CpuDetsRepository cpuDetsRepository;

	public HashMap<String,Integer> findAll(){
						
		HashMap<String,Integer> cpuDets = new HashMap<String,Integer>();
		
		for (CpuDets cpuDetsEach : cpuDetsRepository.findAll()) {
			
			cpuDets.put(cpuDetsEach.getName(), cpuDetsEach.getCpuCore());
			
		}
		
		return cpuDets;
		
	}

	public HashMap<String,Integer> SaveAll(HashMap<String,Integer> cpuDetsHash){

		ArrayList<CpuDets> cpuDetsArray = new ArrayList<CpuDets>();
				
		for (String key : cpuDetsHash.keySet()) {
			
			CpuDets cpuDets = new CpuDets();
			
			cpuDets.setName(key);	
			
			cpuDets.setCpuCore(cpuDetsHash.get(key));
			
			cpuDetsArray.add(cpuDets);
					
		}
		
		cpuDetsRepository.saveAll(cpuDetsArray);
		
		return findAll();
		
	}
}
