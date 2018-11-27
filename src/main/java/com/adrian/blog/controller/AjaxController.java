package com.adrian.blog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adrian.blog.model.Modelo;
import com.adrian.blog.service.IModeloService;

@Controller
public class AjaxController {

	private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

	@Autowired
	private IModeloService modeloService;

	@RequestMapping("/cargarlocalidades/{id_marca}")
	public @ResponseBody List<Modelo> modelos(@RequestParam("codigo_provincia") int id_marca) {
		logger.info("modelos" + id_marca);
		System.err.println("Entra controlador " + id_marca);
		List<Modelo> municipios = modeloService.findByIdMarca(id_marca);
		return municipios;
	}

}
