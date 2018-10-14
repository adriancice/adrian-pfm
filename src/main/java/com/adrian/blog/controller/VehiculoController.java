package com.adrian.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VehiculoController {
	private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

	@RequestMapping(value = "/vehiculoAdd", method = RequestMethod.GET)
	public ModelAndView vehiculoAdd() {
		logger.info("vehiculoAdd");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("vehiculoAdd");
		return modelAndView;
	}

}
