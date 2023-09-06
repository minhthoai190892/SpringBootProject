package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

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
		System.err.println("UserService>saveUser");
		boolean isUpdatingUser = (user.getId()!=null);
		System.out.println(isUpdatingUser);
		if (isUpdatingUser) {
			User existingUser = userRepository.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}else {
				encodePassword(user);
			}
		}else {
			encodePassword(user);
		}
		
		return userRepository.save(user);
	}

	/**
	 * Hàm kiểm tra email
	 * 
	 * @return boolean
	 */
	public boolean isEmailUnique(Integer id, String email) {
		System.err.println("UserService>isEmailUnique");
		User user = userRepository.getUserByEmail(email);
		if (user == null) {
			return true;
		}
		boolean isCreatingNew = (id == null);
		System.out.println(isCreatingNew);
		if (isCreatingNew) {
			if (user != null) {
				return false;
			}
		} else {
			if (user.getId() != id) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Hàm lấy user bằng id
	 * 
	 * @return User / thông báo không tìm thấy
	 */
	public User getUserById(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new UserNotFoundException("Could not find any user with ID: " + id);
		}

	}
	public void deleteUserById(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if (countById == null || countById ==0) {
			throw new UserNotFoundException("Could not find any user with ID: " + id);
		}
		userRepository.deleteById(id);
		
	}
}
