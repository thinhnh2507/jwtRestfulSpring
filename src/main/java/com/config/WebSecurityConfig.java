package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.CustomAuthenticationEntryPoint;
import com.jwt.CustomizeAccessDenied;
import com.jwt.JwtAuthenticationFilter;
import com.jwt.PasswordBase64;
import com.urlFilter.UrlAccessDecisionManager;
import com.urlFilter.UrlFilterInvocationSecurityMetadataSource;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userService;
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// Get AuthenticationManager bean
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordBase64();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService) // Cung cáº¥p userservice cho spring security
				.passwordEncoder(passwordEncoder()); // cung cáº¥p password encoder
	}
	
	@Bean
	public FilterInvocationSecurityMetadataSource UrlSecurityMetadataSource() {
		UrlFilterInvocationSecurityMetadataSource securityMetadataSource = new UrlFilterInvocationSecurityMetadataSource();
		return securityMetadataSource;
	}

	@Bean
	public AccessDecisionManager UrlAccessDecisionManager() {
		return new UrlAccessDecisionManager();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable() // Ngăn chặn request từ một domain khác
				.authorizeRequests()
				.anyRequest().authenticated()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						object.setSecurityMetadataSource(UrlSecurityMetadataSource());
						object.setAccessDecisionManager(UrlAccessDecisionManager());
						return object;
					}
					
				})
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				.accessDeniedHandler(new CustomizeAccessDenied())
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
