/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adrian.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The PassEncoding class
 *
 * @author Adrian Paul
 * @version 1.0 Date 14/09/2018.
 */
public class PassEncoding {

	private static PassEncoding passEncoding = new PassEncoding();
	public BCryptPasswordEncoder passwordEncoder;

	public static PassEncoding getInstance() {
		if (passEncoding != null)
			return passEncoding;
		return new PassEncoding();
	}

	private PassEncoding() {
		passwordEncoder = new BCryptPasswordEncoder();
	}

}
