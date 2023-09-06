package com.shopme.admin.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.user.RoleRepository;
import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	public List<User> listAll() {
		return userRepository.findAll();
		
	}
	public List<Role> listRoles() {
		return roleRepository.findAll();
	}
	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
