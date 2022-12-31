package com.example.demo.jwt;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.user_detail.UserDetailServiceCustom;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	private Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

	private final UserDetailServiceCustom userDetailsService;

	@Value("${jwt.header}")
	private String authorizationHeader;
	@Value("${jwt.secret}")
	private String secretKey;
	// 60s * 60p * 12h = 1/2 day
	@Value("${jwt.expiration}")
	private long expirationTime;

	public JwtProvider(UserDetailServiceCustom userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String email) {
		Claims claims = Jwts.claims().setSubject(email);
		System.out.println(claims);
		Date now = new Date();
		Date expirationDay = new Date(now.getTime() + expirationTime * 1000); // expirationTime * 1000 = milliseconds

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expirationDay)
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public boolean isExpiredToken(String token) throws ResourceNotFoundException {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			// expired date < current date => token expired
			if (claimsJws.getBody().getExpiration().before(new Date())) {
				return true;
			}
			return false;
		} catch (JwtException | IllegalArgumentException exception) {
			LOGGER.error("JWT token không hợp lệ!", exception.getMessage());
			throw new ResourceNotFoundException("JWT token đã hết hạn hoặc không hợp lệ!");
		}
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getClientIdEmail(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getClientIdEmail(String token) {

		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest request) {
		return request.getHeader(authorizationHeader);
	}
}
