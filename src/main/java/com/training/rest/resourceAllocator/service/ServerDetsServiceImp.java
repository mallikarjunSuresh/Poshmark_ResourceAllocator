package com.training.rest.resourceAllocator.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.rest.resourceAllocator.model.ServerDets;
import com.training.rest.resourceAllocator.repository.ServerDetsRepository;

@Service
public class ServerDetsServiceImp implements ServerDetsService {
	
	@Autowired
	ServerDetsRepository serverDetsRepository;
	
	public ServerDetsServiceImp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<String,HashMap<String,Float>> saveAll(HashMap<String,HashMap<String,Float>> serverDetails) 
	
	{
		HashMap<String,Float> cpuPrice = new HashMap<String,Float>();
		
		for (String region : serverDetails.keySet()) {
			
			cpuPrice = serverDetails.get(region);
			
			Float price = new Float(0.00f);
			
			for (String cpu : cpuPrice.keySet()) {
				
				price = cpuPrice.get(cpu);
				
				ServerDets serverDets = new ServerDets();
				
				serverDets.setServerName(cpu);
				
				serverDets.setPrice(price);
				
				serverDets.setRegion(region);
				
				serverDetsRepository.save(serverDets);
			}
			
		}
		
		return getAll();
	}
	
	@Override
	public HashMap<String, HashMap<String, Float>> getAll() {
		
		List<String> totalRegions = new ArrayList<String>();
		
		totalRegions = serverDetsRepository.findDistinctRegion();
		
		HashMap<String,HashMap<String,Float>> outServerDetails = new HashMap<String,HashMap<String,Float>>();
		
		outServerDetails.keySet();
	    
	    for (String region : totalRegions) {
	    	
	    	List<ServerDets> serverDetsList = new ArrayList<ServerDets>();
	    	
	    	serverDetsList = serverDetsRepository.findAllByRegion(region);
	    	 
	    	HashMap<String,Float> outCpuPrice = new HashMap<String,Float>();
	    		    	
	    	for(ServerDets serverDets : serverDetsList) {
	    		  	
		    	outCpuPrice.put(serverDets.getServerName(), serverDets.getPrice());
	    		
	    	}
	    	
	    	outServerDetails.put(region, outCpuPrice);
	    	
	    }
    	
		return outServerDetails;
	}		

}
