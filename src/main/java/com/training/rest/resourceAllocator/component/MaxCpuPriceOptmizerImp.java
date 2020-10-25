package com.training.rest.resourceAllocator.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.training.rest.resourceAllocator.service.CpuDetsService;
import com.training.rest.resourceAllocator.service.CpuDetsServiceImp;

public class MaxCpuPriceOptmizerImp implements Callable<HashMap<String,Object>>{
	

	private HashMap<String,Integer> cpuDets;
	
	private HashMap<String,Float> priceRegion;
	
	private ArrayList<String> server;
	
	private String name;
	
	private Integer hours;

	private float priceNeeded;
		
	private CpuDetsService cpuDetsService;
	
	@Override
	public HashMap<String,Object> call() throws Exception{
				
		cpuDets = cpuDetsService.findAll();
				
		ArrayList<Object> out = new ArrayList<Object>();
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		result.put("region", name);
				
		out = recurFunc(0, (priceNeeded - (priceRegion.get(server.get(0))) * hours)  ,0, 0,
		new HashMap<String,Integer>(), new HashMap<String,Integer>());
		
		result.put("total_price",priceNeeded);
		
		result.put("total_cpu",out.get(0));
		
		result.put("server",out.get(1));
		
		return result;
	};
		
	public MaxCpuPriceOptmizerImp(ArrayList<String> server, HashMap<String,Float> priceRegion, int hours, String name, 
			float priceNeeded, CpuDetsService cpuDetsService) {
		super();
		
		this.priceRegion = priceRegion;
		
		this.server = server;
		
		this.hours = hours;
		
		this.name = name;
		
		this.priceNeeded = priceNeeded;	
		
		this.cpuDetsService = cpuDetsService;
		// TODO Auto-generated constructor stub
	}

/* Recursive function to calculate different combination of servers and finding the most cpu combination.
 * 
 * User "Unbounded Knapsack" algorithm, without "memoization", because of the continous nature of the price
 * not possible for splitting into sub problem result in O(n^2) complexity. 
 *  
 */
	
	private ArrayList<Object> recurFunc(int posStart, float priceRemaining,
			int cpus, int maxCpus , HashMap<String,Integer> currServers,
			HashMap<String,Integer> selectedServe) {

/* Out array holds current max cpu possible on pos 1 and server combination for that on pos 2 */
		
		ArrayList<Object> out = new ArrayList<Object>();

// Base case for recursion when all cost has exhausted.		
		if(priceRemaining <= 0) {
			
			maxCpus = Math.max(maxCpus, cpus);

// Compare current cpu with previous max cpu and update new maxCpu and server combination for that.			
			if(maxCpus == cpus) {
				selectedServe = currServers;
			}
			
	    	
			out.add(0, maxCpus);
			out.add(1, selectedServe);
			
			return out;
					
		}
	
// Initialization
		String serverName = new String(server.get(posStart));
		currServers = (HashMap<String,Integer>) currServers.clone();
		int count = currServers.getOrDefault(serverName, 0);

// Cpu addition and server combination added for current recursion.			
		cpus  = cpus + this.cpuDets.get(server.get(posStart));				
		currServers.put(serverName, count+1);
	
		
		for (int i = posStart; i < server.size(); i++) {
			
		    out = recurFunc(i, (priceRemaining - (priceRegion.get(server.get(i))) * hours), cpus, maxCpus, currServers, selectedServe);

// Collection the current best combination to pass it to next recursion.
		    maxCpus = (Integer) out.get(0);
		    
		    selectedServe = (HashMap<String,Integer>) out.get(1);
		    		      			
		}	
	
		return out;
				
	}	
}
