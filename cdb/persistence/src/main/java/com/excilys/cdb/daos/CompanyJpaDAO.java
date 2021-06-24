package com.excilys.cdb.daos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;



@Repository
public interface CompanyJpaDAO extends JpaRepository <Company, Integer>{

	public long count();
	public Company findById(int id);
	
}
