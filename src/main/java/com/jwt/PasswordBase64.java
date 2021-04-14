package com.jwt;

import java.util.Base64;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordBase64 implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		if (rawPassword == null) {
			throw new IllegalArgumentException("rawPassword cannot be null");
		}
		String text = rawPassword.toString();
		byte[] bs = text.getBytes();
		return Base64.getEncoder().encodeToString(bs) ;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return Base64.getEncoder().encodeToString(rawPassword.toString().getBytes()).equals(encodedPassword);
	}

}
