package com.adrian.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.adrian.blog.model.User;
import com.adrian.blog.service.IUserService;

/**
 * The GlobalController Class
 *
 * @author Adrian Paul
 * @version 1.0 Date 14/09/2018.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GlobalController {

	@Autowired
	private IUserService userService;

	private User loginUser;

	public User getLoginUser() {
		if (loginUser == null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			loginUser = userService.findByUserName(auth.getName());
		}
		return loginUser;
	}
}
