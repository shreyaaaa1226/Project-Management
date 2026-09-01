package com.shreya.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenValidator extends OncePerRequestFilter {

	private final JwtService jwtService;

	public JwtTokenValidator(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtHeader = request.getHeader("Authorization");

		if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
			try {
				Claims claims = jwtService.getClaims(jwtHeader);
				String email = String.valueOf(claims.get("email"));
				String authorities = String.valueOf(claims.get("authorities"));
				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				SecurityContextHolder.clearContext();
				throw new BadCredentialsException("Invalid JWT token", e);
			}
		}

		filterChain.doFilter(request, response);
	}
}
