package com.adrian.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.blog.model.User;
import com.adrian.blog.paginator.PageRender;
import com.adrian.blog.service.UserService;
import com.adrian.blog.utils.PassEncoding;
import com.adrian.blog.utils.Roles;

/**
 * The UserController Class
 *
 * @author Adrian Paul
 * @version 1.0 Date 14/09/2018.
 */
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	GlobalController globalController;

	@Autowired
	UserService userService;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("reqUser", new User());
		logger.info("login");
		return "login";
	}

	@RequestMapping({ "/home", "/" })
	public String home(Model model) {
		logger.info("home");
		return "home";
	}

	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("reqUser", new User());
		logger.info("register");
		return "register";
	}

	@RequestMapping(value = { "/user/register" }, method = RequestMethod.POST)
	public String register(@ModelAttribute("reqUser") User reqUser, final RedirectAttributes redirectAttributes) {

		logger.info("/user/register");
		User user = userService.findByUserName(reqUser.getUsername());
		if (user != null) {
			redirectAttributes.addFlashAttribute("saveUser", "exist-name");
			return "redirect:/register";
		}
		user = userService.findByEmail(reqUser.getEmail());
		if (user != null) {
			redirectAttributes.addFlashAttribute("saveUser", "exist-email");
			return "redirect:/register";
		}

		reqUser.setPassword(PassEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
		reqUser.setRole(Roles.ROLE_ADMIN.getValue());

		if (userService.save(reqUser) != null) {
			redirectAttributes.addFlashAttribute("saveUser", "success");
		} else {
			redirectAttributes.addFlashAttribute("saveUser", "fail");
		}

		return "redirect:/register";
	}

	@RequestMapping(value = { "/listar" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		logger.info("listar");

		Pageable pageRequest = PageRequest.of(page, 4);
		Page<User> usuarios = userService.findAll(pageRequest);

		PageRender<User> pageRender = new PageRender<>("/listar", usuarios);
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);

		return "listar";
	}

}
