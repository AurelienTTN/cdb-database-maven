package com.excilys.cdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.daos.UserJpaDAO;
import com.excilys.cdb.model.MyUserDetails;
import com.excilys.cdb.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	 
    @Autowired
    private UserJpaDAO userRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new MyUserDetails(user);
    }

}
