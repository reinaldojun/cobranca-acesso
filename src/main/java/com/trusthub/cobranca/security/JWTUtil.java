package com.trusthub.cobranca.security;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.trusthub.cobranca.application.util.Constantes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	
	/**
	 * Gerar Token com base64
	 * @param auth
	 * @return
	 */
	public String generateToken(Authentication auth) {
		
			String id = String.valueOf(((UserSS) auth.getPrincipal()).getId());
			String username = ((UserSS) auth.getPrincipal()).getUsername();
			Collection<?> authorities = ((UserSS) auth.getPrincipal()).getAuthorities();
			Set<String> perfis = authorities.stream().map(authoritie -> authoritie.toString().substring(5)).collect(Collectors.toSet());
			
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
			Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
			
			return Jwts.builder()
					.setId(id)
					.setSubject(username)
					.setExpiration(new Date(System.currentTimeMillis() + expiration))
					.claim(Constantes.PERFIS, perfis)
					.signWith(SignatureAlgorithm.HS256, signingKey)
					.compact();
	}
	
	/**
	 * Validar o token
	 * @param token
	 * @return
	 */
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Pega o UserName (email)
	 * @param token
	 * @return
	 */
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	/**
	 * recupera o Claims com base64
	 * @param token
	 * @return
	 */
	public Claims getClaims(String token) {
		try {
			 return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Recupera a chave Secreta
	 * @return
	 */
	public String getSecret() {
		return secret;
	}

}