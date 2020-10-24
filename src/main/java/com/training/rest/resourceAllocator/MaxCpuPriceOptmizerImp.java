package com.training.rest.resourceAllocator;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training.rest.resourceAllocator.service.CpuDetsService;

@Component
public class MaxCpuPriceOptmizerImp implements MaxCpuPriceOptmizer{
	
	@Autowired
	CpuDetsService cpuDetsService;	

	private HashMap<String,Integer> cpuDets;
	
	@Override
	public ArrayList<Object> getMaxCpu(ArrayList<String> server, HashMap<String,Float> priceRegion, float priceNeeded){
		
		cpuDets = cpuDetsService.findAll();
				
		return helper(0, priceNeeded , priceRegion , server ,0, 0,
		new HashMap<String,Integer>(), new HashMap<String,Integer>());
	};
		
	public MaxCpuPriceOptmizerImp() {
		super();
		// TODO Auto-generated constructor stub
	}

	private ArrayList<Object> helper(int posStart, float priceRemaining,
			HashMap<String,Float> priceRegion , ArrayList<String> server,
			int cpus, int maxCpus , HashMap<String,Integer> currServers,
			HashMap<String,Integer> selectedServe) {

		ArrayList<Object> out = new ArrayList<Object>();

		if(priceRemaining < 0) {
			maxCpus = Math.max(maxCpus, cpus);
			if(maxCpus == cpus) {
				selectedServe = currServers;
			}
			
	    	
			out.add(0, maxCpus);
			out.add(1, selectedServe);
			
			return out;
					
		}
				
		String name = new String(server.get(posStart));
		
		cpus  = cpus + this.cpuDets.get(server.get(posStart));
		
		currServers = (HashMap<String,Integer>) currServers.clone();
				
		    try {
		    	int count = currServers.get(name);
		    	currServers.put(name,count+1);
		    	
		      } catch (Exception e) {
		    	  currServers.put(name,1);  
		      }

				
		for (int i = posStart; i < server.size(); i++) {
			
		    out = helper(i, (priceRemaining - priceRegion.get(server.get(i)))
		    		, priceRegion, server, cpus, maxCpus, currServers, selectedServe);
		    
		    maxCpus = (Integer) out.get(0);
		    
		    selectedServe = (HashMap<String,Integer>) out.get(1);
		    		      			
		}	
	    	
		return out;
			
//		
	}	
}
