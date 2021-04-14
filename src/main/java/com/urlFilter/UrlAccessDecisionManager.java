package com.urlFilter;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
//		for (ConfigAttribute ca : configAttributes) {
//			// permissions required for the current url request
//			String needRole = ca.getAttribute();
//			// the role of the current user
//			Collection<? extends GrantedAuthority> authorities = CustomUserDetail.getAuthoritiesaa();
////			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//			for (GrantedAuthority authority : authorities) {
//				// Just include one of the roles to access
//				if (authority.getAuthority().equals(needRole)) {
//					return;
//				}
//			}
//		}
//		throw new AccessDeniedException("Please contact the administrator to assign permissions!");
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while(ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ca.getAttribute();
			for(GrantedAuthority ga : authentication.getAuthorities()) {
				if(ga.getAuthority().equals(needRole)) {
					return;
				}
			}
		}
		throw new AccessDeniedException("Please contact the administrator to assign permissions!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
