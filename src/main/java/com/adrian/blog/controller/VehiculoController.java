package com.adrian.blog.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.blog.model.User;
import com.adrian.blog.model.Vehiculo;
import com.adrian.blog.security.AuthUserDetailsService;
import com.adrian.blog.service.IUploadFileService;
import com.adrian.blog.service.IUserService;
import com.adrian.blog.service.IVehiculoService;

@Controller
@SessionAttributes("vehiculo")
public class VehiculoController {
	private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

	@Autowired
	IVehiculoService vehiculoService;

	@Autowired
	IUploadFileService uploadFileService;

	@Autowired
	AuthUserDetailsService userDetailsService;

	@Autowired
	IUserService userService;

	@RequestMapping(value = "/vehiculoAdd", method = RequestMethod.GET)
	public ModelAndView vehiculoAdd() {
		logger.info("vehiculoAdd");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("vehiculoAdd");
		return modelAndView;
	}

	@RequestMapping("/crearAnuncio")
	public String register(Model model) {
		model.addAttribute("vehiculo", new Vehiculo());
		model.addAttribute("titulo", "Crear anuncio");
		logger.info("crearAnuncio");
		return "crearAnuncio";
	}

	/**
	 * metodo para editar los anuncios
	 * 
	 * @param id
	 * @param model
	 * @param flash
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/crearAnuncio/{id}")
	public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash,
			Locale locale, Authentication authentication) {
		User u = userDetailsService.getUserDetail(authentication.getName());

		Vehiculo vehiculo = null;

		if (id > 0) {
			vehiculo = vehiculoService.findById(id);
			if (vehiculo == null) {
				flash.addFlashAttribute("error", "No existe ningun anuncio con este id !");
				return "redirect:/misAnuncios";
			}
			if (vehiculo.getIdUser() != u.getId()) {
				flash.addFlashAttribute("error", "No puedes editar este anuncio !");
				return "redirect:/misAnuncios";
			}
		} else {
			flash.addFlashAttribute("error", "El id del vehiculo no puede ser 0 o negativo !");
			return "redirect:/misAnuncios";
		}
		model.put("vehiculo", vehiculo);
		model.put("titulo", "Editar anuncio");
		return "crearAnuncio";
	}

	@RequestMapping(value = { "/vehiculo/crearAnuncio" }, method = RequestMethod.POST)
	public String crearAnuncio(@ModelAttribute("vehiculo") Vehiculo vehiculo, @RequestParam("file") MultipartFile foto,
			final RedirectAttributes redirectAttributes, Authentication authentication, SessionStatus status) {

		logger.info("/vehiculo/crearAnuncio");
		User u = userDetailsService.getUserDetail(authentication.getName());
		Vehiculo veh = vehiculo;
		if (!foto.isEmpty()) {
			if (vehiculo.getId() > 0 && vehiculo.getFoto() != null && vehiculo.getFoto().length() > 0) {
				uploadFileService.delete(vehiculo.getFoto());
			}
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			veh.setFoto(uniqueFilename);
		}
		veh.setProvincia(u.getProvincia());
		veh.setIdUser(u.getId());
		vehiculoService.save(veh);
		status.setComplete();
		redirectAttributes.addFlashAttribute("saveVehiculo", "success");

		return "redirect:/crearAnuncio";
	}

	/**
	 * metodo para mostrar las fotos que estan en la carpeta 'uploads' de la raiz
	 * del proyecto
	 * 
	 * @param filename
	 * @return
	 */
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@RequestMapping(value = "/anuncio/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") int id, RedirectAttributes flash, Locale locale) {
		logger.info("anuncio/eliminar");
		if (id > 0) {
			Vehiculo vehiculo = vehiculoService.findById(id);
			vehiculoService.delete(vehiculo);
			flash.addFlashAttribute("success", "Anuncio eliminado correctamente");
			if (uploadFileService.delete(vehiculo.getFoto())) {
				flash.addFlashAttribute("info", "Foto borrada correctamente");
			}
		}
		return "redirect:/misAnuncios";
	}

	@RequestMapping("/anuncio/detalle/{id}")
	public String subscribe(@PathVariable(value = "id") int id, Model model) {
		logger.info("anuncio/detalle");
		Vehiculo vehiculo = vehiculoService.findById(id);
		model.addAttribute("vehiculo", vehiculo);
		model.addAttribute("user", userService.findById(vehiculo.getIdUser()));

		return "detallesAnuncio";
	}

}
