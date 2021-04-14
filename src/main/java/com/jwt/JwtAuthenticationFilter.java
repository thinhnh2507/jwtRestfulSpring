package com.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.config.UserService;

import io.jsonwebtoken.JwtException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
		@Autowired
		private JwtTokenProvider tokenProvider;
		@Autowired
		private UserService customUserDetailsService;

		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			try {
				// Lấy jwt từ request
				String jwt = getJwtFromRequest(request);
				if (jwt != null && tokenProvider.validateToken(jwt)) {
					String email = tokenProvider.getUsernameFromJWT(jwt);
					UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
					// Nếu người dùng hợp lệ, set thông tin cho Seturity Context
					if (userDetails != null) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}

			} catch (JwtException ex) {
				ex.getMessage();
			}
			filterChain.doFilter(request, response);
		}
		private String getJwtFromRequest(HttpServletRequest request) {
			String bearerToken = request.getHeader("Authorization");
			// Kiểm tra xem header Authorization có chứa thông tin jwt không
			if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
				return bearerToken.substring(7);
			}
			return null;
		}


}

