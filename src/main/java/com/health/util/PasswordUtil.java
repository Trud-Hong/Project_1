package com.health.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordUtil {
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static String encode(String password) {
		return encoder.encode(password);
	}
	
	public static boolean match(String password, String encodedPassword) {
		return encoder.matches(password, encodedPassword);
		
	}

}
