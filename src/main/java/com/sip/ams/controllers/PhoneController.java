package com.sip.ams.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;

import com.sip.ams.entities.Phone;
import com.sip.ams.entities.User;
import com.sip.ams.entities.Category;
import com.sip.ams.repositories.PhoneRepository;
import com.sip.ams.repositories.UserRepository;
import com.sip.ams.repositories.CategoryRepository;

@Controller
@RequestMapping("/phone/")
public class PhoneController {

	private final PhoneRepository phoneRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	public PhoneController(PhoneRepository phoneRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
		this.phoneRepository = phoneRepository;
		this.categoryRepository = categoryRepository;
		this.userRepository=userRepository;
	}
	
	@GetMapping("list")
	public String listPhone(Model model) {
	
		List<Phone> la = (List<Phone>) phoneRepository.findAll();

		if (la.size() == 0)
			la = null;

		model.addAttribute("phones", la);
		return "phone/list";
	}
	
	@GetMapping("listphone")
	public String listPhoneUser(Model model) {
	
		List<Phone> la = (List<Phone>) phoneRepository.findAll();

		if (la.size() == 0)
			la = null;

		model.addAttribute("phones", la);
		return "phone/listPhone";
	}
	
	@GetMapping("add")
	public String showAddPhoneForm(Model model) {

		model.addAttribute("categorys", categoryRepository.findAll());
		model.addAttribute("phone", new Phone());
		return "phone/addPhone";
	}

	@PostMapping("add")
	// @ResponseBody
	public String addPhone(@Valid Phone phone, BindingResult result,
			@RequestParam(name = "categoryId", required = false) int p,Model model) {
		
		
		if (result.hasErrors()) {
			model.addAttribute("categorys", categoryRepository.findAll());
			return "phone/addPhone";
		}

		Category category = categoryRepository.findById(p)
				.orElseThrow(() -> new IllegalArgumentException("Invalid catgory Id:" + p));
		
		phone.setProvider(category);

		phoneRepository.save(phone);
		return "redirect:list";


	}
	
	@GetMapping("delete/{id}")
	public String deletePhone(@PathVariable("id") long id, Model model) {
		Phone phone = phoneRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
		
		phoneRepository.delete(phone);
		return "redirect:../list";
	}
	
	@GetMapping("edit/{id}")
	public String showPhoneFormToUpdate(@PathVariable("id") long id, Model model) {
		Phone phone = phoneRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));

		model.addAttribute("phone", phone);
		model.addAttribute("categorys", categoryRepository.findAll());
		model.addAttribute("idCategory", phone.getProvider().getId());

		return "phone/updatePhone";
	}

	@PostMapping("edit")
	public String updatePhone(@Valid Phone phone, BindingResult result, Model model,
			@RequestParam(name = "categoryId", required = false) int p) {
		if (result.hasErrors()) {
			
			return "phone/updatePhone";
		}

		Category category = categoryRepository.findById(p)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + p));
		phone.setProvider(category);

		phoneRepository.save(phone);
		return "redirect:list";
	}
	

	@GetMapping("buy/{id}")
	public String buy(@PathVariable("id") long id, Model model) {
		Phone phone = phoneRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
	
		model.addAttribute("phone", phone);
		model.addAttribute("categorys", phone.getProvider().getName());
		

		return "phone/buyPhone";
	}
	
	  @GetMapping("buydelete/{id}") 
	  public String buyhone(@PathVariable("id") long id, Model model) 
	  { Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  String username = auth.getName();
		  Phone phone = phoneRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid category Id:" + id));
	  sendEmail(username, true);
	  phoneRepository.delete(phone);
	  return "redirect:../list"; }

	/*
	 * @RequestMapping(value = "/email", method = RequestMethod.GET)
	 * 
	 * @GetMapping("buydelete/{id}") //@ResponseBody public String
	 * enableUserAcount(@PathVariable ("id") long id, Principal principal) { String
	 * email=principal.getName(); sendEmail(email, true); Phone phone =
	 * phoneRepository.findById(id) .orElseThrow(()-> new
	 * IllegalArgumentException("Invalid category Id:" + id));
	 * 
	 * phoneRepository.delete(phone); return "redirect:../../list"; }
	 */
	void sendEmail(String email, boolean state) {

	        SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(email);
	        if(state == true)
	        {
	        msg.setSubject("Confirmation");
	        msg.setText("Hello, Your ordered has been comfirmed. "
	        		+ 
	        		"You can log in : http://127.0.0.1:81/login"
	        		+ " \n Best Regards!");
	        }
	        else
	        {
	        	msg.setSubject("Failed Ordered");
	            msg.setText("Hello, Your ordered has been failed.");
	        }
	        javaMailSender.send(msg);

	    }
		
	

}
