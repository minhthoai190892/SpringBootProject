package com.shopme;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shopme.admin.user.RoleRepository;
import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@SpringBootApplication
@EntityScan({"com.shopme.common.entity","com.shopme.admin.user"})
public class ShopmeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopmeBackEndApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository,UserRepository userRepository) {
		return runner ->{
//			createRole(repository);
//			createUserWithTwoRole(repository,userRepository);
//			listUser(userRepository);
//			getUserById(userRepository);
//			updateUserDetail(userRepository);
//			updateUserRoles(userRepository,roleRepository);
//			deleteUserById(userRepository);
//			testEncodePassword();
		};
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
		listUsers.forEach(user->System.out.println(user));
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
	private void createUser(RoleRepository roleRepository,UserRepository userRepository) {
		
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
		Role roleSalesperson = new Role("Salesperson","manage product price, customers, shipping, orders and sales report");
		Role roleEdit = new Role("Editor","manage categories, brands, products,articles and menus");
		Role roleShipper = new Role("Shipper","view products, view orders and update order status");
		Role roleAssistant = new Role("Assistant","manage questions and reviews");
		repository.saveAll(List.of(roleSalesperson,roleEdit,roleShipper,roleAssistant));
	}
}
