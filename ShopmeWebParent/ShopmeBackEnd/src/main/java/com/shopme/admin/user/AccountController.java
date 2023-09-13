package com.shopme.admin.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.security.ShopmeUserDetailsService;
import com.shopme.common.entity.User;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AccountController {
	@Autowired
	private UserService userService;

	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser, Model model,
			HttpServletRequest request) {
		System.err.println("AccountController>viewDetails");
		String email = loggedUser.getUsername();
		System.out.println(email);
		User user = userService.getByEmail(email);

		String test = getEmailOfAuthenticatedCustomer(request);
		System.out.println("test: " + test);
		model.addAttribute("user", user);
		return "account_form";

	}

	@PostMapping("/account/update")
	public String saveUser(User user, RedirectAttributes redirectAttributes,@AuthenticationPrincipal ShopmeUserDetails loggedUser, HttpServletRequest request,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		System.err.println("/users/save");
	

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("fileName " + fileName);
			user.setPhoto(fileName);
			User saveUser = userService.updateAccount(user);
			String uploadDir = "user-photos/" + saveUser.getId();
			System.out.println(uploadDir);
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getPhoto().isEmpty()) {
				user.setPhoto(null);
			}
			userService.updateAccount(user);
		}
		//set first name and last name of ShopmeUserDetails
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setlastName(user.getLastName());
		redirectAttributes.addFlashAttribute("message", "Your account details have been updated.");
//		System.out.println(user);
//		System.out.println(user.getRoles());
		return "redirect:/account";
	}

	public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
		Object principal = request.getUserPrincipal();
		if (principal == null) {
			return null;
		}
		String customerEmail = null;
		customerEmail = request.getUserPrincipal().getName();
		System.err.println("customerEmail>>>>>>" + customerEmail);

		return customerEmail;
	}
}
