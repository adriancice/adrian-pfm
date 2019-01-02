package com.adrian.blog.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.blog.model.Filtro;
import com.adrian.blog.model.Foto;
import com.adrian.blog.model.Marca;
import com.adrian.blog.model.Modelo;
import com.adrian.blog.model.User;
import com.adrian.blog.model.Vehiculo;
import com.adrian.blog.security.AuthUserDetailsService;
import com.adrian.blog.service.EmailService;
import com.adrian.blog.service.IAnioService;
import com.adrian.blog.service.ICombustibleService;
import com.adrian.blog.service.IFavoritoService;
import com.adrian.blog.service.IFotoService;
import com.adrian.blog.service.IMarcaService;
import com.adrian.blog.service.IModeloService;
import com.adrian.blog.service.IProvinciaService;
import com.adrian.blog.service.IUploadFileService;
import com.adrian.blog.service.IUserService;
import com.adrian.blog.service.IVehiculoService;

@Controller
@SessionAttributes("vehiculo")
public class VehiculoController {
	private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

	private static final String fotoDefault = "default_photo.jpg";
	@Autowired
	IVehiculoService vehiculoService;

	@Autowired
	IUploadFileService uploadFileService;

	@Autowired
	AuthUserDetailsService userDetailsService;

	@Autowired
	IUserService userService;

	@Autowired
	IProvinciaService provinciaService;

	@Autowired
	IMarcaService marcaService;

	@Autowired
	IAnioService anioService;

	@Autowired
	ICombustibleService combustibleService;

	@Autowired
	IFotoService fotoService;

	@Autowired
	EmailService emailService;

	@Autowired
	IModeloService modeloService;

	@Autowired
	IFavoritoService favoritoService;

	@RequestMapping(value = "/vehiculoAdd", method = RequestMethod.GET)
	public String vehiculoAdd(Model model) {
		logger.info("vehiculoAdd");
		model.addAttribute("listaMarcas", marcaService.findAll());
		return "vehiculoAdd";
	}

	/**
	 * metodo para añadir un nuevo vehiculo en la bbdd, eligieno una marca podremos
	 * añadir nuevos modelos de momento no funciona, me da el error -> Duplicate
	 * entry '0' for key 'PRIMARY' <-
	 * 
	 * @param model
	 * @param flash
	 * @param req
	 * @return
	 */
	@RequestMapping("/add-modelo")
	public String addModelo(Model model, RedirectAttributes flash, HttpServletRequest req) {
		logger.info("addModelo");
		try {
			Modelo m = new Modelo();
			m.setIdMarca(Integer.parseInt(req.getParameter("idMarca")));
			m.setModelo(req.getParameter("modelo"));
			modeloService.save(m);
			flash.addFlashAttribute("success", "Has añadido un nuevo vehiculo !");
		} catch (Exception e) {
			flash.addFlashAttribute("error", "No se ha podido añadir vehiculo nuevo !");
			e.printStackTrace();
		}
		return "redirect:/vehiculoAdd";
	}

	@RequestMapping("/crearAnuncio")
	public String register(Model model) {
		model.addAttribute("vehiculo", new Vehiculo());
		model.addAttribute("titulo", "Crear anuncio");
		logger.info("crearAnuncio");
		model.addAttribute("listaMarcas", marcaService.findAll());
		model.addAttribute("listaAnios", anioService.findAll());
		model.addAttribute("listaCombustibles", combustibleService.findAll());
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
	public String editar(@PathVariable(value = "id") Integer id, Model model, RedirectAttributes flash, Locale locale, Authentication authentication, HttpServletRequest req) {
		logger.info("editarAnuncio");
		Vehiculo vehiculo = null;
		try {
			User u = userDetailsService.getUserDetail(authentication.getName());

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
			vehiculo.setMarca(String.valueOf(marcaService.findByMarca(vehiculo.getMarca()).getIdMarca()));
			System.err.println("1+ " + vehiculo.getModelo());
			vehiculo.setModelo("2");
			System.err.println("2+ " + vehiculo.getModelo());
			model.addAttribute("listaMarcas", marcaService.findAll());
			model.addAttribute("listaProvincias", provinciaService.findAll());
			model.addAttribute("vehiculo", vehiculo);
			model.addAttribute("titulo", "Editar anuncio");
			model.addAttribute("listaAnios", anioService.findAll());
			model.addAttribute("listaCombustibles", combustibleService.findAll());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "crearAnuncio";
	}

	/**
	 * Metodo para crear un nuevo anuncio
	 * 
	 * @param vehiculo
	 * @param foto
	 * @param redirectAttributes
	 * @param authentication
	 * @param status
	 * @param flash
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/vehiculo/crearAnuncio" }, method = RequestMethod.POST)
	public String crearAnuncio(@ModelAttribute("vehiculo") Vehiculo vehiculo, @RequestParam("files") MultipartFile[] foto, final RedirectAttributes redirectAttributes,
			Authentication authentication, SessionStatus status, RedirectAttributes flash, Model model) {
		logger.info("/vehiculo/crearAnuncio");
		Vehiculo veh = vehiculo;
		System.err.println("marca: " + veh.getMarca());
		try {
			String mensaje = "El anuncio se ha creado con exito !";

			if (vehiculo.getId() != 0) {
				mensaje = "Has editado correctamente el anuncio !";
				flash.addFlashAttribute("success", mensaje);
			}
			User u = userDetailsService.getUserDetail(authentication.getName());

			veh.setMarca(marcaService.findByIdMarca(Integer.parseInt(veh.getMarca())).getMarca());
			veh.setProvincia(u.getProvincia());
			veh.setIdUser(u.getId());
			vehiculoService.save(veh);

			String uniqueFilename = null;
			Arrays.asList(foto).stream().map(file -> {
				try {
					return uploadFileService.copy(file, veh.getId());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return uniqueFilename;
			}).collect(Collectors.toList());

			status.setComplete();
			flash.addFlashAttribute("success", mensaje);
			redirectAttributes.addFlashAttribute("saveVehiculo", "success");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return "redirect:/anuncio/detalle/" + veh.getId();
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
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"").body(recurso);
	}

	/**
	 * metodo para eliminar un anuncio El usuario solo puede borrar sus anuncios, no
	 * los de otros
	 * 
	 * @param id
	 * @param flash
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/anuncio/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") int id, RedirectAttributes flash, Locale locale) {
		logger.info("anuncio/eliminar");
		try {
			if (id > 0) {
				Vehiculo vehiculo = vehiculoService.findById(id);
				vehiculoService.delete(vehiculo);
				flash.addFlashAttribute("success", "Anuncio eliminado correctamente");
				if (!vehiculo.getFoto().equals(fotoDefault)) {
					uploadFileService.delete(vehiculo.getFoto());
					flash.addFlashAttribute("info", "Foto borrada correctamente");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/misAnuncios";
	}

	/**
	 * Metodo para ver los detalles de cada anuncio
	 * 
	 * @param id
	 * @param model
	 * @param authentication
	 * @return
	 */
	@RequestMapping("/anuncio/detalle/{id}")
	public String anuncioDetalle(@PathVariable(value = "id") int id, Model model, Authentication authentication, RedirectAttributes flash) {
		logger.info("anuncio/detalle");
		try {
			User u = userDetailsService.getUserDetail(authentication.getName());
			model.addAttribute("myUser", u);
			if (favoritoService.existeFav(id, u.getId()) != null) {
				model.addAttribute("existeFav", "existeFav");
			}
			Vehiculo veh = vehiculoService.findById(id);
			if (veh == null) {
				flash.addFlashAttribute("error", "El anuncio con el id '" + id + "' no existe !");
				return "redirect:/";
			}
			Collection<Foto> fotos = fotoService.findByIdVehiculo(id);

			veh.setFoto(fotos.iterator().next().getFoto());
			vehiculoService.save(veh);
			Vehiculo vehiculo = vehiculoService.findById(id);
			model.addAttribute("vehiculo", vehiculo);
			model.addAttribute("user", userService.findById(vehiculo.getIdUser()));
			List<Foto> fotosA = (List<Foto>) fotos;
			fotosA.remove(0);
			model.addAttribute("fotosA", fotosA);
		} catch (Exception e) {
			System.err.println("No hay ningun user logueado !");
		}

		return "detallesAnuncio";
	}

	/**
	 * Metodo para filtrar los anuncios
	 * 
	 * @param page
	 * @param reqFiltro
	 * @param model
	 * @param req
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/vehiculos/filtrarPalabraClave/")
	public String home(@RequestParam(name = "page", defaultValue = "0") int page, @ModelAttribute("reqFiltro") Filtro reqFiltro, Model model, HttpServletRequest req,
			final RedirectAttributes redirectAttributes) {
		logger.info("index");
		try {
			model.addAttribute("listaVehiculos", vehiculoService.filtrar(reqFiltro));
			model.addAttribute("total", vehiculoService.totalVehiculos(vehiculoService.filtrar(reqFiltro)));
			if (vehiculoService.filtrar(reqFiltro).isEmpty()) {
				model.addAttribute("palabra", "No se han encontrado resultados");
			}
			model.addAttribute("limpiar", "limpiar");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "index";
	}

	/**
	 * Metodo para enviar un email al anunciante
	 * 
	 * @param model
	 * @param authentication
	 * @param req
	 * @param flash
	 * @return
	 */
	@RequestMapping(value = "/contactar-anunciante", method = RequestMethod.POST)
	public String contactarAnunciante(Model model, Authentication authentication, HttpServletRequest req, RedirectAttributes flash) {
		logger.info("contactar-anunciante");
		Integer id = Integer.parseInt(req.getParameter("id"));
		try {
			String descripcion = req.getParameter("descripcion");
			if (descripcion.isEmpty()) {
				descripcion = "Estoy interesado en este vehiculo...";
			}
			Vehiculo veh = vehiculoService.findById(id);
			User u = userService.findById(veh.getIdUser());

			// Email message
			SimpleMailMessage contacto = new SimpleMailMessage();
			contacto.setTo(u.getEmail());
			contacto.setSubject("Anuncio " + veh.getMarca() + " " + veh.getModelo());
			contacto.setText("Tienes un mensaje sobre tu anuncio en nuestra web\n\n" + descripcion + "\nNombre: " + req.getParameter("username") + "\nTelefono: "
					+ req.getParameter("telefono") + "\nEmail: " + req.getParameter("email") + "\n\n Por favor no conteste a este correo");
			emailService.sendEmail(contacto);

			flash.addFlashAttribute("success", "Mensaje enviado correctamente !");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/anuncio/detalle/" + id;
	}

	/**
	 * Metodo para compartir el anuncio a traves del email
	 * 
	 * @param email
	 * @param model
	 * @param authentication
	 * @param req
	 * @param flash
	 * @return
	 */
	@RequestMapping(value = "/shareAnuncio", method = RequestMethod.POST)
	public String shareAnuncio(@RequestParam("email") String email, Model model, Authentication authentication, HttpServletRequest req, RedirectAttributes flash) {
		logger.info("contactar-anunciante");
		Integer id = Integer.parseInt(req.getParameter("id"));
		try {
			Vehiculo veh = vehiculoService.findById(id);
			String appUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
			// Email message
			SimpleMailMessage shareEmail = new SimpleMailMessage();
			shareEmail.setTo(email);
			shareEmail.setSubject("Coches: Un amigo te recomienda este anuncio");
			shareEmail.setText("¡Hola!\n" + "Un amigo se ha acordado de ti al ver este anuncio " + veh.getMarca() + " y cee que te puede interesar." + "\n¿Tienes curiosidad?\n"
					+ appUrl + "/anuncio/detalle/" + id);
			emailService.sendEmail(shareEmail);
			model.addAttribute("emailSend", "El correo se envio correctamente");
			logger.info("Email enviado correctamente");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/anuncio/detalle/" + id;
	}

}
