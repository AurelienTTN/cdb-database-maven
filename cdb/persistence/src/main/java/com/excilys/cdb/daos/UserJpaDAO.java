package com.excilys.cdb.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.User;

@Repository
public interface UserJpaDAO extends JpaRepository <User, Integer> {

		public User getUserByUsername(String username);
		
}
