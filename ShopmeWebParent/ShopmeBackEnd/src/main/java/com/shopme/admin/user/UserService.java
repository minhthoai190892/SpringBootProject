package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private void encodePassword(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
	}
	
	public List<User> listAll() {
		return userRepository.findAll();
		
	}
	public List<Role> listRoles() {
		return roleRepository.findAll();
	}
	public User saveUser(User user) {
		encodePassword(user); 
		return userRepository.save(user);
	}
	/**
	 * Hàm kiểm tra email 
	 * @return boolean
	 * */
	public boolean isEmailUnique(String email) {
		User user = userRepository.getUserByEmail(email);
		return user==null;
	}
}
