package com.adrian.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
	private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

	@RequestMapping(value = "/contacto", method = RequestMethod.GET)
	public ModelAndView contacto() {
		logger.info("contacto");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("contacto");
		return modelAndView;
	}
}
