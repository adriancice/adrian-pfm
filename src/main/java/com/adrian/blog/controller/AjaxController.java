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

	/**
	 * metodo para cargar la lista de los modelos segun la marca del coche
	 * @param id_marca
	 * @return
	 */
	@RequestMapping("/cargarModelos/{id_marca}")
	public @ResponseBody List<Modelo> modelos(@RequestParam("id_marca") int id_marca) {
		logger.info("cargar_modelos");
		List<Modelo> modelos = modeloService.findByIdMarca(id_marca);
		return modelos;
	}

}
