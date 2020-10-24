package com.training.rest.resourceAllocator.component;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

public interface MinCpuOptmizer {

	public ArrayList<Object> getMaxCpu(ArrayList<String> cpu, HashMap<String,Float> price,int cpuRemaining);
		
}
