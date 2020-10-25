package com.training.rest.resourceAllocator.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.training.rest.resourceAllocator.service.CpuDetsService;
import com.training.rest.resourceAllocator.service.CpuDetsServiceImp;

public class MinCpuOptmizerImp implements Callable<HashMap<String,Object>>{
	
	private ArrayList<String> cpu;
	
	private HashMap<String,Float> price;
	
	private int cpuNeeded;
	
	private int hours;
	
	private String name;
	
	private float priceNeeded;
	
	private CpuDetsService cpuDetsService;
	
	public MinCpuOptmizerImp(ArrayList<String> cpu, HashMap<String,Float> price,int cpuNeeded, int hours, String name , 
			float priceNeeded, CpuDetsService cpuDetsService) {
		super();
		
		this.cpu = cpu;
		
		this.price = price;
		
		this.cpuNeeded = cpuNeeded;
		
		this.hours = hours;
		
		this.name = name;
		
		this.priceNeeded = priceNeeded;
		
		this.cpuDetsService = cpuDetsService;
				// TODO Auto-generated constructor stub
	}

		
	@Override	
	public HashMap<String,Object> call(){
	
/* Using "unbounded knapsack" for calculating the min cost for the required cpu. Memoization technique
is used to divide the problem into subproblem. Result in reducing time complexity form O(n^2) to O(n*m). */	
		
/* Initializing required details */
		
		/* Collecting cpuDets from cpuDets data service */
		
		HashMap<String,Integer> cpuDets = new HashMap<String,Integer>();
				
		cpuDets = cpuDetsService.findAll();
		
		HashMap<String,Object> result = new HashMap<String,Object>();
	    
	    int numberOfServer = cpu.size();
	    
/* Price for sub problems are stored in priceArr and pathIndex hold the index(int numbers are used) of server combination stored in pathValue */	
	    
	    float[][] priceArr = new float[numberOfServer][cpuNeeded + 1];
	    
	    int[][] pathIndex = new int[numberOfServer][cpuNeeded + 1];
	    
	    HashMap<Integer, HashMap<String,Integer>> pathValue = new HashMap<Integer, HashMap<String,Integer>>();
	    
	    HashMap<String,Integer> defPath = new HashMap<String,Integer>();
	    
	    defPath.put("large", 0);
	    
	    pathValue.put(0, defPath);
	    
	    int count = 1;
	    
	    for(int i = 0; i < numberOfServer; i++) {
	    	
	      for(int j = 1; j <= cpuNeeded; j++) {
	    	  
	    	pathIndex[i][j] = count;
	    	  
	    	if(priceArr[i][j] == 0.0) {
	    		priceArr[i][j] = 10000.0f;
	    	}

/* Defaulting  with some large price value , for comparing calculated price on minimization */	
	    	
	        float includingCurrentCpuPrice = 10000.0f;
	        float excludingCurrentCpuPrice = 10000.0f;
	        
	        HashMap<String,Integer> incPath = new HashMap<String,Integer>();
	        HashMap<String,Integer> excPath = new HashMap<String,Integer>();
	          
/* Include the cpu until capacity is remaining */	
	        
	        if(cpuDets.get(cpu.get(i)) <= j) {
	        	
	        	includingCurrentCpuPrice = (price.get(cpu.get(i)) * hours + priceArr[i][j - cpuDets.get(cpu.get(i))]);
	          
	          try {
	              incPath = (HashMap<String,Integer>) pathValue.get(pathIndex[i][j - cpuDets.get(cpu.get(i))]).clone(); 
	              
	              if(incPath==null) {
	            	  
	            	  incPath = (HashMap<String,Integer>) defPath.clone(); 
	              }	  
	          } catch (Exception e) {
	        	  
	          }
	          
	          if (incPath.containsKey(cpu.get(i))) {
	        	  incPath.put(cpu.get(i), incPath.get(cpu.get(i)) +1);
	          } else {
	        	  incPath.put(cpu.get(i),1);
	          }
	        	         
	        }
	        

/* Excluding need to happen when more than one server combination is selected. */
	        
	        if(i > 0) {
	        	
	          if(priceArr[i - 1][j]==0.0) {
	        	  excludingCurrentCpuPrice = 10000.0f;
	          } else {
	        	  excludingCurrentCpuPrice = priceArr[i - 1][j];
	          }
	          try {
	        	  excPath = (HashMap<String,Integer>) pathValue.get(pathIndex[i-1][j]).clone();
	        	  
	        	  if(pathValue.get(pathIndex[i-1][j]) == null) {
	        		  excPath = (HashMap<String,Integer>) defPath.clone();
	        	  }
	          } catch (Exception e) {
	        	  
	          }          
	        }
	        
	        
	        
	        if(includingCurrentCpuPrice > excludingCurrentCpuPrice) {
	        	
	        	pathValue.put(pathIndex[i][j],excPath);     	
	        	
	        	priceArr[i][j] = excludingCurrentCpuPrice;
	        	
	        } else {
	        	
	        	pathValue.put(pathIndex[i][j],incPath); 
	        	
	        	priceArr[i][j] = includingCurrentCpuPrice;
	        }
	       
	        count++;
	      }
	    }

/* Removing the default server combination used */	    
	    if(pathValue.get(pathIndex[numberOfServer - 1][cpuNeeded]).get("large") == 0) {
	    	
	    	pathValue.get(pathIndex[numberOfServer - 1][cpuNeeded]).remove("large");
	    }

	    
/* Result updation */
		result.put("region", name);
		
		result.put("total_cpu",cpuNeeded);
		
		result.put("total_price",priceArr[numberOfServer - 1][cpuNeeded]);
		
		result.put("server",pathValue.get(pathIndex[numberOfServer - 1][cpuNeeded]));

/* If price condtion is not met then return no server string */		
		if (priceNeeded != 0 && priceArr[numberOfServer - 1][cpuNeeded] > priceNeeded)
		{
			result.put("total_price",priceNeeded);
			result.put("total_cpu",0);
			result.put("server","no server available");
		}
		
		return result;
		
	};
}
