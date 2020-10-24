package com.training.rest.resourceAllocator;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class Result {
	
	private String region;

	private Float total_cost;
	
	private HashMap<String,Integer> servers;

	public Result() {
		
	}
	
	public Result(String region, Float total_cost, HashMap<String,Integer> servers) {
		super();
		this.region = region;
		this.total_cost = total_cost;
		this.servers = servers;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Float getTotal_cost() {
		return total_cost;
	}

	public void setTotal_cost(Float total_cost) {
		this.total_cost = total_cost;
	}

	public HashMap<String,Integer> getServers() {
		return servers;
	}

	public void setServers(HashMap<String,Integer> servers) {
		this.servers = servers;
	}
	
	
	
}
