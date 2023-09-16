package com.shopme.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopme.common.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	@Query("select c from Category c where c.parent.id is null")
	public List<Category> listRootCategory();
}
