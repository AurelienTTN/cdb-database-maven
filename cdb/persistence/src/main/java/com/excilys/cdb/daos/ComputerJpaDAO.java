package com.excilys.cdb.daos;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Computer;

@Repository
public interface ComputerJpaDAO extends JpaRepository <Computer, Integer>{

	
	public Computer findById(int id);
	List<Computer> findByNameLike(String recherche,Pageable pageable);
	long countByNameLike(String search);

	
	
	
}
