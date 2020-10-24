package com.training.rest.resourceAllocator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CPU_DETS")
public class CpuDets {
	
	public CpuDets() {
		super();
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getCpuCore() {
		return CpuCore;
	}

	public void setCpuCore(Integer cpuCore) {
		CpuCore = cpuCore;
	}

	@Id
	private String Name;
	
	private Integer CpuCore;
	
}
