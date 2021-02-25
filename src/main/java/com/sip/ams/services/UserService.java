package com.sip.ams.services;

import com.sip.ams.entities.Role;
import com.sip.ams.entities.Users;
import com.sip.ams.repositories.RoleRepository;
import com.sip.ams.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
@Service("userService")
public class UserService {

	 private UserRepository userRepository;
	    private RoleRepository roleRepository;
	    private BCryptPasswordEncoder bCryptPasswordEncoder;
	    @Autowired
	    public UserService(UserRepository userRepository,
	                       RoleRepository roleRepository,
	                       BCryptPasswordEncoder bCryptPasswordEncoder) {
	        this.userRepository = userRepository;
	        this.roleRepository = roleRepository;
	        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	    }

	    public Users findUserByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }

	    public void saveUser(Users users) {
	        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
	        users.setActive(1);
	        Role userRole = roleRepository.findByRole("USER");
	        users.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	        userRepository.save(users);
	    }

}
