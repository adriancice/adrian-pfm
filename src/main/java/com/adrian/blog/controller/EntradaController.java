package com.adrian.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.blog.model.Entrada;
import com.adrian.blog.service.IEntradaService;

/**
 * Clase controladora de las entradas
 * 
 * @author Adrian Stan
 *
 */

@Controller
public class EntradaController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	//@Autowired
	//IEntradaService entradaService;

	@RequestMapping("/crear-entrada")
	public String crearEntrada(Model model) {
		logger.info("crear-entrada");
		return "crear-entrada";
	}

	@RequestMapping(value = { "/entrada-add" }, method = RequestMethod.POST)
	public String entradaAdd(@ModelAttribute("reqEntrada") Entrada reqEntrada, final RedirectAttributes attributes) {
		logger.info("/entrada-add");

		reqEntrada.setTitulo(reqEntrada.getTitulo());
		reqEntrada.setCategoria(reqEntrada.getCategoria());
		//reqEntrada.setContenido(reqEntrada.getCategoria());

		return "redirect:crear-entrada";
	}

}
