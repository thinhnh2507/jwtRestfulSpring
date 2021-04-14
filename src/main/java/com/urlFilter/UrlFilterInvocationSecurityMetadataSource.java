package com.urlFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.model.Group;
import com.model.Permission;
import com.repository.PermissionRepository;
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
	
	@Autowired
	PermissionRepository permissionRepository;
	@Bean
    public AntPathMatcher getAntPathMatcher() {
        return new AntPathMatcher();
    }
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		List<Permission> permissionsList = permissionRepository.findAll();
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		Collection<ConfigAttribute> rs = new ArrayList<ConfigAttribute>();
		for(Permission permissionUri : permissionsList) {
			if(getAntPathMatcher().match(permissionUri.getUri(), requestUrl)) {
				for (Group g : permissionUri.getGroups()) {
	                rs.addAll(SecurityConfig.createList("ROLE_".concat(g.getName().toUpperCase())));
	            }
			}
		}
		if(rs.size()>0) {
			return rs ; 
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
