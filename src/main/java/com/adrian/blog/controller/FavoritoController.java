package com.adrian.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.blog.model.Favorito;
import com.adrian.blog.model.User;
import com.adrian.blog.security.AuthUserDetailsService;
import com.adrian.blog.service.IFavoritoService;
import com.adrian.blog.service.IVehiculoService;

@Controller
public class FavoritoController {
	private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

	@Autowired
	private IFavoritoService favoritoService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Autowired
	private AuthUserDetailsService userDetailsService;

	@RequestMapping("/misFavoritos")
	public String misFavoritos(Model model, Authentication authentication, RedirectAttributes flash) {
		logger.info("misFavoritos");
		User u = userDetailsService.getUserDetail(authentication.getName());
		model.addAttribute("listaFavoritos", vehiculoService.findVehiculosByUserFavorito(u.getId()));
		if (vehiculoService.findVehiculosByUserFavorito(u.getId()) == null) {
			model.addAttribute("vacio", "vacio");
		}
		model.addAttribute("myUser", u);
		return "misFavoritos";
	}

	@RequestMapping(value = "/addFavorito", method = RequestMethod.GET)
	public String addFavorito(RedirectAttributes flash, @RequestParam("id_user") int idUser, @RequestParam("id_vehiculo") int idVehiculo) {
		logger.info("addFavorito");
		if (favoritoService.existeFav(idVehiculo, idUser) == null) {
			Favorito fav = new Favorito();
			fav.setIdUser(idUser);
			fav.setIdVehiculo(idVehiculo);
			favoritoService.save(fav);
			flash.addFlashAttribute("success", "Has añadido este vehiculo a tu lista de favoritos !");
		} else {
			flash.addFlashAttribute("error", "Ya has añadido este vehiculo a tu lista de favoritos !");
		}
		return "redirect:/anuncio/detalle/" + idVehiculo;
	}

	@RequestMapping(value = "/deleteFavoritoUser", method = RequestMethod.GET)
	public String deleteFavoritoUser(@RequestParam("id_user") int idUser, @RequestParam("id_vehiculo") int idVehiculo, RedirectAttributes flash, HttpServletRequest request) {
		logger.info("deleteFavorito");
		Favorito fav = favoritoService.existeFav(idVehiculo, idUser);
		favoritoService.delete(fav);
		flash.addFlashAttribute("info", "Has eliminado el vehiculo de tu lista de favoritos !");
		// probando aver si encuentro la ruta
		System.err.println("REQ: " + request.getRequestURI());
		return "redirect:/misFavoritos";
	}

	@RequestMapping(value = "/deleteFavorito", method = RequestMethod.GET)
	public String deleteFavorito(@RequestParam("id_user") int idUser, @RequestParam("id_vehiculo") int idVehiculo, RedirectAttributes flash, HttpServletRequest request) {
		logger.info("deleteFavorito");
		Favorito fav = favoritoService.existeFav(idVehiculo, idUser);
		favoritoService.delete(fav);
		flash.addFlashAttribute("info", "Has eliminado el vehiculo de tu lista de favoritos !");
		// probando aver si encuentro la ruta
		System.err.println("REQ: " + request.getRequestURI());
		return "redirect:/anuncio/detalle/" + idVehiculo;
	}

}
