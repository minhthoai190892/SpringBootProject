package com.shopme.admin.user;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.export.UserCsvExporter;
import com.shopme.admin.export.UserExcelExporter;
import com.shopme.admin.export.UserPDFExporter;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String listFirstPage(Model model) {
		return listByPage(1, model,"firstName","asc",null);
	}

	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") Integer pageNum, Model model,@Param("sortField")String sortField,@Param("sortDir")String sortDir,@Param("keyword")String keyword) {
		System.err.println("UserController > /users/page/{pageNum}" + pageNum);
		System.out.println("sortField "+sortField);
		System.out.println("sortDir "+sortDir);
		Page<User> page = userService.listByPage(pageNum,sortField,sortDir,keyword);
		List<User> listUsers = page.getContent();

		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc")?"desc":"asc";
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("keyword", keyword);

		return "users/users";
	}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		List<Role> listRoles = userService.listRoles();

		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "users/user_form";
	}

	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		System.err.println("/users/save");
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("fileName " + fileName);
			user.setPhoto(fileName);
			User saveUser = userService.saveUser(user);
			String uploadDir = "user-photos/" + saveUser.getId();
			System.out.println(uploadDir);
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getPhoto().isEmpty()) {
				user.setPhoto(null);
			}
			userService.saveUser(user);
		}

		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
//		System.out.println(user);
//		System.out.println(user.getRoles());
		return getRedirectURLtoAffectedUser(user);
	}
	/**
	 * Hàm hiển thị User sau khi cập nhật hoặc tạo mới
	 * @return đường dẫn tới User
	 * */
	private String getRedirectURLtoAffectedUser(User user) {
		String firstPartOfEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword="+firstPartOfEmail;
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {
		try {
			User user = userService.getUserById(id);
			List<Role> listRoles = userService.listRoles();
			model.addAttribute("user", user);
			model.addAttribute("listRoles", listRoles);
			model.addAttribute("pageTitle", "Edit User(ID: " + id + ")");
			return "users/user_form";
		} catch (UserNotFoundException e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
		}

	}

	@GetMapping("/users/delete/{id}")
	public String deleteById(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		try {

			userService.deleteUserById(id);
			redirectAttributes.addFlashAttribute("message", "The user ID " + id + " has been deleted successfully");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());

		}
		return "redirect:/users";
	}

	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		userService.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
	}
	@GetMapping("/users/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll();
		UserCsvExporter exporter = new UserCsvExporter();
		exporter.export(listUsers, response);
	}
	@GetMapping("/users/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll();
		UserExcelExporter exporter = new UserExcelExporter();
		exporter.export(listUsers, response);
	}
	@GetMapping("/users/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll();
		UserPDFExporter exporter = new UserPDFExporter();
		exporter.export(listUsers, response);
	}
}
