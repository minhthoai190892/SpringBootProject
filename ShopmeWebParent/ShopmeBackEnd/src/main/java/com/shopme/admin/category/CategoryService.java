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
		return categoryRepository.findAll();

	}
	/**
	 * hàm hiển thị danh sách category cho trang html
	 * */
	public List<Category> listCategoriesUsedInForm() {
		List<Category> categoriesUsedInForm = new ArrayList<>();
		Iterable<Category> categoriesInDB = categoryRepository.findAll();
		for (Category category : categoriesInDB) {
			if (category.getParent()==null) {
				categoriesUsedInForm.add(new Category(category.getName()));
				System.out.println(category.getName());
//				lấy tập hợp con của parent
				Set<Category> children = category.getChildren();
				for (Category subCategory : children) {
					System.out.println("--"+subCategory.getName());
					String name = "--"+subCategory.getName();
					categoriesUsedInForm.add(new Category(name));
					
					listChildrent(categoriesUsedInForm,subCategory,1);
				}
			}
		}
		return categoriesUsedInForm;
		
	}
	private void listChildrent(List<Category> categoriesUsedInForm,Category parent, int subLevel) {
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
			categoriesUsedInForm.add(new Category(name));
			listChildrent(categoriesUsedInForm,subCategory, newSubLevel);
		}
		
	}
}
