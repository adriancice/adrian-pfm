package com.adrian.blog.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.blog.model.User;
import com.adrian.blog.service.EmailService;
import com.adrian.blog.service.IUserService;
import com.adrian.blog.utils.PassEncoding;

/**
 * controlador que se encarga de reestablecer la contraseña olvidada
 * 
 * @author Adrian Stan
 *
 */
@Controller
public class PasswordController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private EmailService emailService;

	/**
	 * metodo que manda un correo con un token para reestablecer la contraseña
	 * 
	 * @param userEmail
	 * @param flash
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String processForgotPasswordForm(@RequestParam("email") String userEmail, final RedirectAttributes flash, HttpServletRequest request, Model model) {
		logger.info("processForgotPasswordForm");

		// Lookup user in database by e-mail
		User optional = userService.findByEmail(userEmail);
		if (optional == null) {
			flash.addFlashAttribute("error", "Email '" + userEmail + "' doesn´t exist !");
			return "redirect:/login";
		} else {
			optional.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			userService.save(optional);
			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setTo(optional.getEmail());
			passwordResetEmail.setSubject("Password reset request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?token=" + optional.getResetToken());
			emailService.sendEmail(passwordResetEmail);
			model.addAttribute("emailSend", "El correo se envio correctamente");
			logger.info("Email enviado correctamente");
		}
		flash.addFlashAttribute("success", "Password reset link send successful !");
		return "redirect:/login";
	}

	// Display form to reset password
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String displayResetPasswordPage(@RequestParam("token") String token, Model model) {
		logger.info("displayResetPasswordPage");
		System.err.println("El token es: ' " + token + " '");
		User user = userService.findByResetToken(token);

		if (user != null) {
			model.addAttribute("resetToken", token);
		} else {
			model.addAttribute("errorToken", "Oops!  This is an invalid password reset link.");
		}

		return "resetPassword";
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public String setNewPassword(@RequestParam("reset_password") String resetPassword, @RequestParam("tok") String token, Model model) {
		logger.info("setNewPassword");

		// Find the user associated with the reset token
		User user = userService.findByResetToken(token);

		// This should always be non-null but we check just in case
		if (user != null) {
			// Set new password
			user.setPassword(PassEncoding.getInstance().passwordEncoder.encode(resetPassword));

			// Set the reset token to null so it cannot be used again
			user.setResetToken(null);

			// Save user
			userService.save(user);
			System.err.println("Has cambiado la pass ok!");
			return "login";
		} else {
			return "forgot";
		}

	}

	// Going to reset page without a token redirects to login page
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}

}
