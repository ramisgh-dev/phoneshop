package com.sip.ams.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sip.ams.entities.Users;
import com.sip.ams.entities.Role;
import com.sip.ams.repositories.RoleRepository;
import com.sip.ams.repositories.UserRepository;

@Controller
@RequestMapping("/accounts/")

public class AccountController {

	private final UserRepository userRepository;	
	private final RoleRepository roleRepository;
	

	
	@Autowired
    public AccountController(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
	
	@GetMapping("list")
    public String listUsers(Model model) {
    	
    	List<Users> users = (List<Users>) userRepository.findAll();
    	long nbr =  userRepository.count();
    
    	if(users.size()==0)
    		users = null;
        model.addAttribute("user", users);
        model.addAttribute("nbr", nbr);
        return "users/listusers";
    }
	
	@GetMapping("enable/{id}")
	//@ResponseBody
    public String enableUserAcount(@PathVariable ("id") int id) {
    	
		
		 Users users = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid User Id:" + id));
	     users.setActive(1);
	     userRepository.save(users);
    	return "redirect:../list";
    }
	
	@GetMapping("disable/{id}")
	//@ResponseBody
	public String disableUserAcount(@PathVariable ("id") int id) {
    	
		 Users users = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid User Id:" + id));
	     users.setActive(0);
	     userRepository.save(users);
    	return "redirect:../list";
    }

    
	
@PostMapping("updateRole")
//@ResponseBody
public String UpdateUserRole(@RequestParam ("id") int id,
		@RequestParam ("newrole")String newRole
		) {
	
	 Users users = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid User Id:" + id));
     
	 Role userRole = roleRepository.findByRole(newRole);
	 
     users.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
     
     userRepository.save(users);
	return "redirect:list";
}


	
	


}
