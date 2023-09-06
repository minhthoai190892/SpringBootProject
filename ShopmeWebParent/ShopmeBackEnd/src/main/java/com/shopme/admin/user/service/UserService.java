package com.shopme.admin.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired 
	private UserRepository userRepository;
	public List<User> listAll() {
		return userRepository.findAll();
		
	}
}
