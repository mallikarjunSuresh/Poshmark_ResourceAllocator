package com.training.rest.resourceAllocator.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.rest.resourceAllocator.model.CpuDets;

@Repository
public interface CpuDetsRepository extends JpaRepository<CpuDets, String>{
	
	List<CpuDets> findAll(); 
			
}

