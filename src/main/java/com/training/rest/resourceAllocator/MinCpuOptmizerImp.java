package com.training.rest.resourceAllocator;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training.rest.resourceAllocator.service.CpuDetsService;

@Component
public class MinCpuOptmizerImp implements MinCpuOptmizer{
	
	public MinCpuOptmizerImp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public CpuDetsService cpuDetsService;
	
	@Override	
	public ArrayList<Object> getMaxCpu(ArrayList<String> cpu, HashMap<String,Float> price,int cpuRemaining){
		
		HashMap<String,Integer> cpuDets = new HashMap<String,Integer>();
		
		cpuDets = cpuDetsService.findAll();
		
	    ArrayList<Object> out = new ArrayList<Object>();
	    
	    int numberOfWeights = cpu.size();
	    
	    float[][] arr = new float[numberOfWeights][cpuRemaining + 1];
	    
	    int[][] pathIndex = new int[numberOfWeights][cpuRemaining + 1];
	    
	    HashMap<Integer, HashMap<String,Integer>> pathValue = new HashMap<Integer, HashMap<String,Integer>>();
	    
	    HashMap<String,Integer> defPath = new HashMap<String,Integer>();
	    
	    defPath.put("large", 0);
	    
	    pathValue.put(0, defPath);
	    
	    int count = 1;

	        
	    for(int i = 0; i < numberOfWeights; i++) {
	    	
	      for(int j = 1; j <= cpuRemaining; j++) {
	    	  
	    	pathIndex[i][j] = count;
	    	  
	    	if(arr[i][j] == 0.0) {
	    		arr[i][j] = 10000.0f;
	    	}
	        
	        float includingCurrentWeightProfit = 10000.0f;
	        float excludingCurrentWeightProfit = 10000.0f;
	        
	        HashMap<String,Integer> incPath = new HashMap<String,Integer>();
	        HashMap<String,Integer> excPath = new HashMap<String,Integer>();
	          
	        
	        if(cpuDets.get(cpu.get(i)) <= j) {
	        	
	          includingCurrentWeightProfit = price.get(cpu.get(i)) + arr[i][j - cpuDets.get(cpu.get(i))];
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
	        
	             
	        if(i > 0) {
	        	
	          if(arr[i - 1][j]==0.0) {
	        	  excludingCurrentWeightProfit = 10000.0f;
	          } else {
	        	  excludingCurrentWeightProfit = arr[i - 1][j];
	          }
	          try {
	        	  excPath = (HashMap<String,Integer>) pathValue.get(pathIndex[i-1][j]).clone();
	        	  
	        	  if(pathValue.get(pathIndex[i-1][j]) == null) {
	        		  excPath = (HashMap<String,Integer>) defPath.clone();
	        	  }
	          } catch (Exception e) {
	        	  
	          }          
	        }
	        
	        
	        
	        if(includingCurrentWeightProfit > excludingCurrentWeightProfit) {
	        	
	        	pathValue.put(pathIndex[i][j],excPath);     	
	        	
	        	arr[i][j] = excludingCurrentWeightProfit;
	        	
	        } else {
	        	
	        	pathValue.put(pathIndex[i][j],incPath); 
	        	
	        	arr[i][j] = includingCurrentWeightProfit;
	        }
	       
	        count++;
	      }
	    }
	    
	    out.add(0, arr[numberOfWeights - 1][cpuRemaining]);
		out.add(1, pathValue.get(pathIndex[numberOfWeights - 1][cpuRemaining]));
		
		return out;
		
	};
}
