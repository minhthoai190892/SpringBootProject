package com.shopme;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shopme.admin.category.CategoryRepository;
import com.shopme.admin.user.RoleRepository;
import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@SpringBootApplication
@EntityScan({ "com.shopme.common.entity", "com.shopme.admin.user" })
public class ShopmeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopmeBackEndApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository, UserRepository userRepository,
			CategoryRepository categoryRepository) {
		return runner -> {
//			createRole(repository);
//			createUserWithTwoRole(repository,userRepository);
//			listUser(userRepository);
//			getUserById(userRepository);
//			updateUserDetail(userRepository);
//			updateUserRoles(userRepository,roleRepository);
//			deleteUserById(userRepository);
//			testEncodePassword();
//			testGetUserById(userRepository);
//			testCountById(userRepository);
//			testDisableUser(userRepository);
//			testListFirstPage(userRepository);
//			testSearch(userRepository);
//			testCreateRootCategory(categoryRepository);
//			testCreateSubCategory(categoryRepository);
//			testListCategory(categoryRepository);
			testListRootCategory(categoryRepository);
		};
	}

	private void testListRootCategory(CategoryRepository categoryRepository) {
		// TODO Auto-generated method stub
		System.err.println("testListCategory");
		List<Category>listRootCategory = categoryRepository.listRootCategory();
		listRootCategory.forEach(cat->System.out.println(cat.getName()));
	}

	private void testListCategory(CategoryRepository categoryRepository) {
		// TODO Auto-generated method stub
		System.err.println("testListCategory");
		Category category = categoryRepository.findById(7).get();
		System.out.println(category);

	}

	private void testCreateSubCategory(CategoryRepository categoryRepository) {
		// TODO Auto-generated method stub
		System.err.println("testCreateSubCategory");
		Category parent = new Category(1);
		Category subCategory = new Category("Lapđtopss", parent);
		categoryRepository.save(subCategory);

	}

	private void testCreateRootCategory(CategoryRepository categoryRepository) {
		// TODO Auto-generated method stub
		System.err.println("testCreateRootCategory");
		Category category = new Category("Computers");
		Category saveCategory = categoryRepository.save(category);
		System.out.println(saveCategory);

	}

	private void testSearch(UserRepository userRepository) {
		System.err.println("testSearch");
		// TODO Auto-generated method stub
		String keyword = "bruce";
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepository.findAll(keyword, pageable);
		List<User> users = page.getContent();
		users.forEach(user -> System.out.println(user));
	}

	private void testListFirstPage(UserRepository userRepository) {
		// TODO Auto-generated method stub
		System.err.println("testListFirstPage");
		int pageNumber = 1;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepository.findAll(pageable);
		List<User> users = page.getContent();
		users.forEach(user -> System.out.println(user));
	}

	private void testDisableUser(UserRepository userRepository) {
		// TODO Auto-generated method stub
		System.err.println("testDisableUser");
		Integer id = 9;
		userRepository.updateEnabledStatus(id, false);

	}

	private void testCountById(UserRepository userRepository) {
		// TODO Auto-generated method stub
		System.err.println("testCountById");
		Integer id = 11;
		Long countById = userRepository.countById(id);
		System.out.println(countById);

	}

	private void testGetUserById(UserRepository userRepository) {
		// TODO Auto-generated method stub
		System.err.println("testGetUserById");
		String email = "minhthoai190892@gmail.com";
		User user = userRepository.getUserByEmail(email);
		System.out.println(user);

	}

	private void testEncodePassword() {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "nam2020";
		String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
		boolean matches = bCryptPasswordEncoder.matches(rawPassword, encodePassword);
		System.out.println(encodePassword);
		System.out.println(matches);
	}

	private void deleteUserById(UserRepository userRepository) {
		// TODO Auto-generated method stub
		System.err.println("deleteUserById");
		userRepository.deleteById(3);
	}

	private void updateUserRoles(UserRepository userRepository, RoleRepository roleRepository) {
		// TODO Auto-generated method stub
		System.err.println("updateUserRoles");
		User user = userRepository.findById(3).get();
		Role roleEditor = roleRepository.findById(3).get();
		Role salespersonRole = roleRepository.findById(2).get();
		user.getRoles().remove(roleEditor);
		user.addRole(salespersonRole);
		userRepository.save(user);

	}

	private void updateUserDetail(UserRepository userRepository) {
		// TODO Auto-generated method stub
		System.err.println("updateUserDetail");
		User user = userRepository.findById(1).get();
		user.setEnabled(true);
		user.setEmail("namjavaprogrammer@gmail.com");
		userRepository.save(user);
	}

	private void getUserById(UserRepository userRepository) {
		// TODO Auto-generated method stub
		System.err.println("getUserById");
		User user = userRepository.findById(1).get();
		System.out.println(user);

	}

	private void listUser(UserRepository userRepository) {
		System.err.println("listUser");
		Iterable<User> listUsers = userRepository.findAll();
		listUsers.forEach(user -> System.out.println(user));
		// TODO Auto-generated method stub

	}

	private void createUserWithTwoRole(RoleRepository roleRepository, UserRepository userRepository) {
		// TODO Auto-generated method stub
		System.err.println("createUserWithTwoRole");
		Role editorRole = roleRepository.findById(3).get();
		Role assistantRole = roleRepository.findById(5).get();
		User user = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
		user.addRole(editorRole);
		user.addRole(assistantRole);
		userRepository.save(user);
	}

	private void createUser(RoleRepository roleRepository, UserRepository userRepository) {

		// TODO Auto-generated method stub
		System.err.println("createUser");
		Role editorRole = roleRepository.findById(3).get();
		Role assistantRole = roleRepository.findById(5).get();
		User user = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
		user.addRole(editorRole);
		user.addRole(assistantRole);
		userRepository.save(user);
	}

	private void createRole(RoleRepository repository) {
		// TODO Auto-generated method stub
		Role roleSalesperson = new Role("Salesperson",
				"manage product price, customers, shipping, orders and sales report");
		Role roleEdit = new Role("Editor", "manage categories, brands, products,articles and menus");
		Role roleShipper = new Role("Shipper", "view products, view orders and update order status");
		Role roleAssistant = new Role("Assistant", "manage questions and reviews");
		repository.saveAll(List.of(roleSalesperson, roleEdit, roleShipper, roleAssistant));
	}
}
