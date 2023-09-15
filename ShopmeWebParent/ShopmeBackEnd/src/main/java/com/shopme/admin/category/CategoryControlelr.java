package com.shopme.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.common.entity.Category;

@Controller
public class CategoryControlelr {
	@Autowired CategoryService categoryService;
	@GetMapping("/categories")
	public String listAll(Model model) {
		List<Category> listCategories= categoryService.listAll();
		model.addAttribute("listCategories",listCategories);
		
		return "categories/categories";
	}
	@GetMapping("/categories/new") 
	public String newCategory(Model model) {
		List<Category> listCategoriesUsedInForm = categoryService.listCategoriesUsedInForm();
		model.addAttribute("category",new Category());
		model.addAttribute("listCategoriesUsedInForm",listCategoriesUsedInForm);
		model.addAttribute("pageTitle","Create New Category");
		return "categories/category_form";
	}
}
