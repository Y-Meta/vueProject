package com.plmm.security.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.plmm.common.core.authority.GrantedAuthority;
import com.plmm.common.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

public class AuthorityUtils {
	public static final List<GrantedAuthority> NO_AUTHORITIES = Collections.emptyList();

	public static List<GrantedAuthority> commaSeparatedStringToAuthorityList(String authorityString) {
		return createAuthorityList(StringUtils.tokenizeToStringArray(authorityString, ","));
	}

	public static Set<String> authorityListToSet(Collection<? extends GrantedAuthority> userAuthorities) {
		Set set = new HashSet(userAuthorities.size());

		for (GrantedAuthority authority : userAuthorities) {
			set.add(authority.getAuthority());
		}

		return set;
	}

	public static List<GrantedAuthority> createAuthorityList(String[] roles) {
		List authorities = new ArrayList(roles.length);

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}
}