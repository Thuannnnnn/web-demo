package com.example.demo.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demo.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtFilter extends GenericFilterBean {
	private final JwtProvider jwtProvider;

	public JwtFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);
			if (token != null && !jwtProvider.isExpiredToken(token)) {
				Authentication authentication = jwtProvider.getAuthentication(token);
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

		} catch (ResourceNotFoundException e) {
			SecurityContextHolder.clearContext();
			handleInvalidCorrelationId((HttpServletResponse) servletResponse, e);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private void handleInvalidCorrelationId(HttpServletResponse response, ResourceNotFoundException e)
			throws IOException {

		ServiceData serviceData = new ServiceData();
		serviceData.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
		serviceData.setErrorDetail("Đã xảy ra lỗi: " + e.getMessage());
		serviceData.setErrorMessage(e.getMessage());

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(serviceData));
	}

	private class ServiceData {
		private HttpStatus errorCode;
		private String errorMessage;
		private Object errorDetail;

		private void setErrorCode(HttpStatus errorCode) {
			this.errorCode = errorCode;
		}

		private void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		private void setErrorDetail(Object errorDetail) {
			this.errorDetail = errorDetail;
		}

	}

}