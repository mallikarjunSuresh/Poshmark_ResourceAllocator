package com.training.rest.resourceAllocator;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training.rest.resourceAllocator.service.ServerDetsService;

@Component
public class Optmizer {
	
	@Autowired
	ServerDetsService serverDetsService;
	
	@Autowired
	MinCpuOptmizer getMinCpuResult;
		
	@Autowired
	MaxCpuPriceOptmizer getMaxCpuPriceResult;
	
	public Optmizer() {
		super();
	}
	
	public ArrayList<Result> startOptmize(int cpuNeeded, float price ,int hours) {
		
		Result result = new Result();
			    
		HashMap<String, HashMap<String, Float>> infoRegion = serverDetsService.getAll();
		
		ArrayList<String> serverRegion = new ArrayList<String>(infoRegion.keySet());
		
		ArrayList<Result> resultList = new ArrayList<Result>();
		
		ArrayList<Object> inter = new ArrayList<Object>();
		
		
		int caseNo = 3;
		
		if(cpuNeeded != 0 && price != 0.0) {
			caseNo = 1;			
		} else {			
			if(cpuNeeded != 0) {
				caseNo = 2;
			} 
		}		
		
		
		for (int i = 0; i < serverRegion.size(); i++) {
			
			HashMap<String,Float> priceRegion = new HashMap<String,Float>(infoRegion.get(serverRegion.get(i)));
			
			ArrayList<String> server = new ArrayList<String>(infoRegion.get(serverRegion.get(i)).keySet());
										
		    switch (caseNo) {
		    
		      case 1:
		    	inter = getMinCpuResult.getMaxCpu(server, priceRegion, cpuNeeded);
		    	
				result.setTotal_cost((Float)inter.get(0));

		      case 2:
				inter = getMinCpuResult.getMaxCpu(server, priceRegion, cpuNeeded);

				result.setTotal_cost((Float)inter.get(0));
				
		      case 3:
		    	  
		    	inter = getMaxCpuPriceResult.getMaxCpu(server, priceRegion, price);  
		    	
		    	result.setTotal_cost(((Integer) inter.get(0)).floatValue());

//				inter = this.helper(0, cpuNeeded , priceRegion , server ,0f, 1000000f,
//						new HashMap<String,Integer>(), new HashMap<String,Integer>(), "null");
		    	
		    }
		    					
			result.setRegion(serverRegion.get(i));
									
			result.setServers((HashMap<String,Integer>)inter.get(1));
			
			resultList.add(result);
						
		}	
	
		return resultList;
	}

		
}
