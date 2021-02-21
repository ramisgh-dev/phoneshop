package com.sip.ams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.sip.ams.entities.Article;
import com.sip.ams.entities.Category;
import com.sip.ams.repositories.CategoryRepository;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/category/")

public class CategoryController {

	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("list")
	// @ResponseBody
	public String listProviders(Model model) {

		List<Category> lp = (List<Category>) categoryRepository.findAll();

		if (lp.size() == 0)
			lp = null;
		model.addAttribute("categorys", lp);

		return "category/listCategory";

	}

	@GetMapping("add")
	public String showAddProviderForm(Model model) {
		Category category = new Category();// object dont la valeur des attributs par defaut
		model.addAttribute("category", category);
		return "category/addCategory";
	}

	@PostMapping("add")
	public String addProvider(@Valid Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "category/addCategory";
		}
		categoryRepository.save(category);
		return "redirect:list";
	}

	@GetMapping("delete/{id}")
	public String deleteProvider(@PathVariable("id") int id, Model model) {

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));

		System.out.println("suite du programme...");

		categoryRepository.delete(category);

		
		return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showProviderFormToUpdate(@PathVariable("id") int id, Model model) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));

		model.addAttribute("category", category);

		return "category/updateCategory";
	}

	@PostMapping("update")
	public String updateProvider(@Valid Category category, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "category/updateCategory";
		}

		categoryRepository.save(category);
		return "redirect:list";

	}

}
