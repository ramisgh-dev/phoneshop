package com.sip.ams.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sip.ams.entities.Category;
import com.sip.ams.entities.Role;
import com.sip.ams.repositories.CategoryRepository;
import com.sip.ams.repositories.RoleRepository;

@Controller
@RequestMapping("/role/")
public class RoleController {
	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleController(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@GetMapping("list")
	// @ResponseBody
	public String listRoles(Model model) {


		List<Role> roles = (List<Role>) roleRepository.findAll();
    	long nbr =  roleRepository.count();
    	if(roles.size()==0)
    		roles = null;
        model.addAttribute("roles", roles);
        model.addAttribute("nbr", nbr);
        return "role/listRole";

	
	}
	
	@GetMapping("add")
	public String showAddProviderForm(Model model) {
		
		return "role/addRole";
	}


	
    @PostMapping("add")
    public String addRole(@RequestParam("role") String role) {
        
        System.out.println(role);
        Role r = new Role(role);
        Role rSaved = roleRepository.save(r);
        System.out.println("role = "+ rSaved);
        return "redirect:list";
    }


}
