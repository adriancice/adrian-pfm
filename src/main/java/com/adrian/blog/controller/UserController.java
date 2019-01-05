package com.adrian.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.blog.model.Filtro;
import com.adrian.blog.model.User;
import com.adrian.blog.model.Vehiculo;
import com.adrian.blog.paginator.PageRender;
import com.adrian.blog.security.AuthUserDetailsService;
import com.adrian.blog.service.IFavoritoService;
import com.adrian.blog.service.IProvinciaService;
import com.adrian.blog.service.IScheduledEmailService;
import com.adrian.blog.service.IUserService;
import com.adrian.blog.service.IVehiculoService;
import com.adrian.blog.utils.EnumRoles;
import com.adrian.blog.utils.PassEncoding;

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
	private IVehiculoService vehiculoService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IProvinciaService provinciaService;

	@Autowired
	private AuthUserDetailsService userDetailsService;

	@Autowired
	private IScheduledEmailService scheduledEmailService;

	@Autowired
	private IFavoritoService favoritoService;

	@RequestMapping("/pruebas")
	public String pruebas(Model model) {
		logger.info("pruebas");
		return "pruebas";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		logger.info("login");
		return "login";
	}

	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	public String home(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		logger.info("index");
		try {
			Pageable pageRequest = PageRequest.of(page, 10);
			Page<Vehiculo> vehiculos = vehiculoService.findAll(pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/", vehiculos);
			model.addAttribute("page", pageRender);

			model.addAttribute("reqFiltro", new Filtro());
			// model.addAttribute("listaVehiculos", vehiculoService.findAllOrderBykm());
			model.addAttribute("listaVehiculos", vehiculos);
			model.addAttribute("total", vehiculoService.totalVehiculos(vehiculoService.findAll()));
			model.addAttribute("paginator", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}

	@RequestMapping("/register")
	public String register(Model model) {
		logger.info("register");
		try {
			model.addAttribute("reqUser", new User());
			model.addAttribute("listaProvincias", provinciaService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "register";
	}

	/**
	 * Metodo para registrar un nuevo usuario Por defecto los usuarios que se
	 * registren tienen el 'ROLE USER'
	 * 
	 * @param reqUser
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = { "/user/register" }, method = RequestMethod.POST)
	public String register(@ModelAttribute("reqUser") User reqUser, final RedirectAttributes redirectAttributes) {
		logger.info("/user/register");
		try {
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
			reqUser.setRole(EnumRoles.ROLE_USER.getValue());

			if (userService.save(reqUser) != null) {
				redirectAttributes.addFlashAttribute("saveUser", "success");
			} else {
				redirectAttributes.addFlashAttribute("saveUser", "fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/register";
	}

	/**
	 * Metodo para listar los usuarios registrados Solo el user con 'ROLE ADMIN'
	 * tiene acceso a esta lista
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = { "/listar" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		logger.info("listar");
		try {
			Pageable pageRequest = PageRequest.of(page, 2);
			Page<User> usuarios = userService.findAll(pageRequest);
			PageRender<User> pageRender = new PageRender<>("/listar", usuarios);
			model.addAttribute("titulo", "Listado de usuarios");
			model.addAttribute("usuarios", usuarios);
			model.addAttribute("page", pageRender);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listar";
	}

	@RequestMapping(value = { "/misAnuncios" }, method = RequestMethod.GET)
	public String misAnuncios(Model model, Authentication authentication, RedirectAttributes flash) {
		logger.info("misAnuncios");
		try {
			User u = userDetailsService.getUserDetail(authentication.getName());
			model.addAttribute("listaVehiculos", vehiculoService.findByIdUser(u.getId()));
			if (vehiculoService.findByIdUser(u.getId()).isEmpty()) {
				model.addAttribute("vacio", "vacio");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "misAnuncios";
	}

	@RequestMapping("/verPerfil")
	public String verPerfil(Model model, Authentication authentication) {
		logger.info("editarCuenta");
		try {
			User u = userDetailsService.getUserDetail(authentication.getName());
			model.addAttribute("suscripcion", scheduledEmailService.existEmail(u.getEmail()));
			model.addAttribute("reqUser", u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "verPerfil";
	}

	@RequestMapping("/editarCuenta/{id}")
	public String editarCuenta(@PathVariable(value = "id") Integer id, Model model, Authentication authentication, RedirectAttributes flash) {
		logger.info("editarCuenta/{id}");
		try {
			User us = userDetailsService.getUserDetail(authentication.getName());
			if (id > 0) {
				User u = userService.findById(id);
				if (u == null) {
					flash.addFlashAttribute("error", "No existe ningun usuario con este id !");
					return "redirect:/verPerfil";
				}
				if (u.getId() != us.getId()) {
					flash.addFlashAttribute("error", "No puedes editar este usuario !");
					return "redirect:/verPerfil";
				}
			} else {
				flash.addFlashAttribute("error", "El id del usuario no puede ser 0 o negativo !");
				return "redirect:/verPerfil";
			}
			model.addAttribute("listaProvincias", provinciaService.findAll());
			model.addAttribute("reqUser", us);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "editarCuenta";
	}

	/*
	 * metodo para editar el perfil del usuario
	 */
	@RequestMapping(value = "/editCuenta", method = RequestMethod.POST)
	public String guardarEditCuenta(@ModelAttribute("reqUser") User reqUser, Model model, HttpServletRequest req, RedirectAttributes flash) {
		logger.info("editCuenta/");
		try {
			User user = userService.findById(reqUser.getId());
			if (reqUser.getEmail() != null) {
				user.setEmail(reqUser.getEmail());
			}

			if (reqUser.getProvincia() != null) {
				user.setProvincia(reqUser.getProvincia());
			}

			if (reqUser.getTelefono() != null) {
				user.setTelefono(reqUser.getTelefono());
			}

			if (!reqUser.getPassword().isEmpty()) {
				System.err.println("input old pass: " + req.getParameter("oldPass"));
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				if (encoder.matches(req.getParameter("oldPass"), user.getPassword())) {// comprobamos si la pass actual es igual que la pass de la bbdd
					user.setPassword(PassEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
				} else {
					flash.addFlashAttribute("error", "La contraseña actual no es correcta !");
					return "redirect:/editarCuenta/" + reqUser.getId();

				}
			}
			userService.save(user);
			flash.addFlashAttribute("success", "Has editado tu perfil correctamente !");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/verPerfil";
	}

	/**
	 * metodo para eliminar la cuenta del usuario
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/borrarCuenta/{id}")
	public String borrarCuenta(@PathVariable(value = "id") Integer id, Authentication authentication, RedirectAttributes flash) {
		logger.info("borrarCuenta/{id}");
		try {
			User us = userDetailsService.getUserDetail(authentication.getName());
			if (us.getId() != id) {
				flash.addFlashAttribute("error", "No tienes poder para eliminar este usuario !");
				return "redirect:/verPerfil";
			}
			scheduledEmailService.delete(us.getEmail());
			favoritoService.deletebyIdUser(id);
			userService.delete(id);
			vehiculoService.deleteByIdUser(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/logout";
	}

}
