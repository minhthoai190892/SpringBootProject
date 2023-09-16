package com.shopme.admin.category;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
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
	@PostMapping("/categories/save")
	public String saveCategory(Category category,@RequestParam("fileImage")MultipartFile multipartFile,RedirectAttributes redirectAttributes) throws IOException {
		System.err.println("CategoryControlelr>saveCategory");
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		System.out.println("fileName: "+fileName);
		category.setImage(fileName);
		
		Category saveCategory = categoryService.saveCategory(category);
		String uploadDir = "../category-images/"+saveCategory.getId();
		//gọi hàm save file
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		redirectAttributes.addFlashAttribute("message", "The category has been saved successfully.");
		return "redirect:/categories";
		
	}
}
