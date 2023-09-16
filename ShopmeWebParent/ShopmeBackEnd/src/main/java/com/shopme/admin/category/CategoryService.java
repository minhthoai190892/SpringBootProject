package com.shopme.admin.category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> listAll() {
//		return categoryRepository.findAll();
		List<Category> rootCategories = categoryRepository.listRootCategory();
		return listHierarchicalCategories(rootCategories);
		

	}
	

/**
 * List Category cho trang categories
 * */	
	private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
		// TODO Auto-generated method stub
		System.err.println("CategoryService>listHierarchicalCategories");
		List<Category> hierarchicalCategories=new ArrayList<>();
//		for (Category category : rootCategories) {
//			System.out.println(category);
//		}
//		
		for (Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyFull(rootCategory));
			Set<Category> children = rootCategory.getChildren();
			for (Category subCategory : children) {
				String name = "--"+subCategory.getName();
				hierarchicalCategories.add(Category.copyFull(subCategory,name));
				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
			}
		}
		
//		for (Category category : hierarchicalCategories) {
//			System.out.println(category);
//		}
		return hierarchicalCategories;
	}
	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,Category parent,int subLevel) {
		Set<Category> children = parent.getChildren();
		int newSubLevel = subLevel+1;
		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name+="--";
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.copyFull(subCategory,name));
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
		}
	}
	
	
	
	
	
	
	/**
	 * hàm hiển thị danh sách category cho trang Create html
	 * */
	public List<Category> listCategoriesUsedInForm() {
		System.err.println("CategoryService>listCategoriesUsedInForm");
		List<Category> categoriesUsedInForm = new ArrayList<>();
		Iterable<Category> categoriesInDB = categoryRepository.findAll();
		for (Category category : categoriesInDB) {
			if (category.getParent()==null) {
				categoriesUsedInForm.add(Category.copyIdAndName(category));
				System.out.println(category.getName());
//				lấy tập hợp con của parent
				Set<Category> children = category.getChildren();
				for (Category subCategory : children) {
					System.out.println("--"+subCategory.getName());
					String name = "--"+subCategory.getName();
					categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
					
					listSubCategoriesUsedInForm(categoriesUsedInForm,subCategory,1);
				}
			}
		}
		return categoriesUsedInForm;
		
	}
	private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm,Category parent, int subLevel) {
		System.err.println("CategoryService>printChildren");
		// TODO Auto-generated method stub
		System.out.println("subLevel: "+subLevel);
		int newSubLevel = subLevel+1;
		System.out.println("newSubLevel: "+newSubLevel);
		Set<Category> children = parent.getChildren();
		for (Category subCategory : children) {
			String name ="";
			for (int i = 0; i < newSubLevel; i++) {
				System.out.print("--");
				name+="--";
			}
			System.out.println(subCategory.getName());
			name+=subCategory.getName();
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
			listSubCategoriesUsedInForm(categoriesUsedInForm,subCategory, newSubLevel);
		}
		
	}
	
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}
}
