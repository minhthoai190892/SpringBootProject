package com.shopme;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import com.shopme.admin.user.RoleRepository;
import com.shopme.common.entity.Role;

@SpringBootApplication
@EntityScan({"com.shopme.common.entity","com.shopme.admin.user"})
public class ShopmeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopmeBackEndApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository repository) {
		return runner ->{
			createRole(repository);
		};
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
