package com.adrian.blog.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.blog.model.ScheduledEmail;
import com.adrian.blog.model.User;
import com.adrian.blog.security.AuthUserDetailsService;
import com.adrian.blog.service.EmailService;
import com.adrian.blog.service.IScheduledEmailService;

@Controller
public class ScheduledTasks {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	private EmailService emailService;

	@Autowired
	private IScheduledEmailService scheduledEmailService;

	@Autowired
	AuthUserDetailsService userDetailsService;

	@RequestMapping("/subscribe/create-task")
	public String createTask(Authentication authentication, HttpServletRequest req, RedirectAttributes flash) {
		logger.info("createTask");

		try {
			User u = userDetailsService.getUserDetail(authentication.getName());
			if (u != null) {
				if (!scheduledEmailService.existEmail(u.getEmail())) {
					ScheduledEmail sEmail = new ScheduledEmail();
					sEmail.setEmail(u.getEmail());
					scheduledEmailService.save(sEmail);
					flash.addFlashAttribute("success", "Te has subscrito correctamente");
				} else {
					flash.addFlashAttribute("warning", "Ya estas subscrito !");
				}

			}
		} catch (Exception e) {
			System.err.println("Tienes que estar logueado para darte de alta en el subscribe " + e);
		}
		return "redirect:/verPerfil";
	}

	@RequestMapping("/subscribe/delete-task")
	public String deleteTask(Authentication authentication, HttpServletRequest req, RedirectAttributes flash) {
		logger.info("deleteTask");

		try {
			User u = userDetailsService.getUserDetail(authentication.getName());
			if (u != null) {
				scheduledEmailService.delete(u.getEmail());
				flash.addFlashAttribute("info", "Te has dado de baja del subscribe !");
			}
		} catch (Exception e) {
			System.err.println("Tienes que estar logueado para darte de baja en el subscribe " + e);
		}
		return "redirect:/verPerfil";
	}

	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void enviarEmailSubscripcion() {
		logger.info("Fixed rated Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		Collection<ScheduledEmail> se = scheduledEmailService.findAll();
		if (!se.isEmpty()) {
			for (ScheduledEmail sce : scheduledEmailService.findAll()) {
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(sce.getEmail());
				email.setSubject("Subscribe prueba");
				email.setText("prueba para ver si se mandan los email de subscribe !\n"
						+ "Si quieres darte de baja del servicio de Newsletters, tienes que iniciar sesion en la pagina de Cars, ver mi perfil y dar de baja suscripcion");
				emailService.sendEmail(email);
				System.err.println("Mensaje enviado a " + sce.getEmail());

			}
		}

	}
}
