package com.adrian.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * controlador de la pagina de error
 * @author Adrian Stan
 *
 */
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

	@RequestMapping("/error")
	public String handleError() {
		// do something like logging
		return "error-404";
	}

	@Override
	public String getErrorPath() {
		return "/error-404";
	}

}
