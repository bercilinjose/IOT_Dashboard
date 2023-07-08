package com.eoxys.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eoxys.security_config.JwtConfig;
import com.eoxys.security_config.PlatformDetailsService;
import com.eoxys.security_config.UserDetailService;

@Component
public class PlatformAuthFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(PlatformAuthFilter.class);

	@Autowired
	private JwtConfig jwtService;

	@Autowired
	private PlatformDetailsService userDetailsService;
	
	@Autowired
	private UserDetailService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		System.out.println("Request Url ===== >" + request.getRequestURI() + "   Request Method ===> " + request.getMethod());
		logger.info("Request Url =====> " + request.getRequestURI() + "  Request Method ===> " + request.getMethod());

		String device_data = "/api/v1/device_data";
		String notification = "/api/v1/notification/v2/ntfy";

		String platform_authenticate = "/api/v1/authenticate";
		String user_login = "/api/v1/user_login";
		String user_authorize = "/api/v1/user_authorize";
		
		String http_device_data = "/api/v1/http_device_data";
		
		
		if (request.getRequestURI().toString().equals(device_data)
				|| request.getRequestURI().toString().equals(notification)) {
			

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
				username = jwtService.extractUsername(token);
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (jwtService.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

		} else if (request.getRequestURI().toString().equals(platform_authenticate)
				|| request.getRequestURI().toString().equals(user_authorize) || request.getRequestURI().toString().equals(user_login) || request.getRequestURI().toString().equals(http_device_data)) {
			
			System.out.println("Non - authorize url");
			logger.info("Non - authorize url");

		}
		
//		else {
//			
//			if (authHeader != null && authHeader.startsWith("Bearer ")) {
//				token = authHeader.substring(7);
//				username = jwtService.extractUsername(token);
//			}
//
//			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//				UserDetails userDetails = userService.loadUserByUsername(username);
//				if (jwtService.validateToken(token, userDetails)) {
//					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
//							null, userDetails.getAuthorities());
//					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//					SecurityContextHolder.getContext().setAuthentication(authToken);
//				}
//			} 
//			
//		}

		filterChain.doFilter(request, response);
	}

}
