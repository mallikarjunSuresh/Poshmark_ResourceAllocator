package com.training.rest.resourceAllocator;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

public interface MaxCpuPriceOptmizer {

	public ArrayList<Object> getMaxCpu(ArrayList<String> server, HashMap<String,Float> priceRegion, float priceNeeded);
}
