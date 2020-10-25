package com.training.rest.resourceAllocator.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training.rest.resourceAllocator.service.CpuDetsService;
import com.training.rest.resourceAllocator.service.ServerDetsService;

@Component
public class OptmizerFacade {
	
	@Autowired
	ServerDetsService serverDetsService;
	
	@Autowired
	CpuDetsService cpuDetsService;
		
	public OptmizerFacade() {
		super();
	}
	
	public ArrayList<HashMap<String,Object>> startOptmize(int cpuNeeded, float price ,int hours) {
	
/* This method will call the required optmizer based on the type of request to provide the require input.
 * method used to find the result are extended callable thread class. Hence executed by executor on multiple thread. */	
		
		HashMap<String, HashMap<String, Float>> serverInfo = serverDetsService.getAll();
		
		ArrayList<String> serverRegion = new ArrayList<String>(serverInfo.keySet());
		
		ArrayList<Future<HashMap<String,Object>>> futureList = new ArrayList<Future<HashMap<String,Object>>>();
		
		ArrayList<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
					
/* Created thread pool for size 4 */
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		
		int caseNo = 0;
		
		if(cpuNeeded == 0 && price != 0.0) {
			caseNo = 1;			
		} 		
				
		for (int i = 0; i < serverRegion.size(); i++) {
			
			HashMap<String,Float> priceRegion = new HashMap<String,Float>(serverInfo.get(serverRegion.get(i)));
			
			ArrayList<String> server = new ArrayList<String>(serverInfo.get(serverRegion.get(i)).keySet());
																
		    switch (caseNo) {
		    				
		      case 1:

/* MaxCpuPriceOptmizer will get the maximum cpu for the price.*/	
		    	  
		    	Callable<HashMap<String,Object>> maxCpuPriceOptmizer = new MaxCpuPriceOptmizerImp(server, priceRegion,
			  			hours, serverRegion.get(i),price,cpuDetsService);
		    	
			    futureList.add(executor.submit(maxCpuPriceOptmizer));
			    					
				break;
				
		      default:
		    	  
/* MinCpuOptmizer will get min price for the cpu needed, if cpu needed is provided. If price also provided,
 * return result if price condition is met. */	
		    	  
			    Callable<HashMap<String,Object>> minCpuOptmizer = new MinCpuOptmizerImp(server, priceRegion, cpuNeeded,
			    			hours, serverRegion.get(i),price,cpuDetsService);
			    				    	
			   	futureList.add(executor.submit(minCpuOptmizer));
			    				    					
				break;				
		    }		    								
		}	
		
        for(Future<HashMap<String,Object>> future : futureList){
        	
            try {
 /* Collect result from callable object to our result list. */  
            	
            	resultList.add(future.get());
            	
            } catch (Exception e) {
            	
            }
        }
        //shut down the executor service now
        executor.shutdown();
	
		return resultList;
	}

		
}
