package com.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.config.CustomUserDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	// Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
	private final String JWT_SECRET = "thinh.nh";

	// Thời gian có hiệu lực của chuỗi jwt
//    public static final long JWT_EXPIRATION = 5 * 60 * 60;
	public static final long JWT_EXPIRATION = 60 * 60 * 60;

	public String generateToken(CustomUserDetail userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		return Jwts.builder().setSubject(userDetails.getUser().getEmail()).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}

	// Lấy thông tin user từ jwt
	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw ex;

		} catch (ExpiredJwtException ex) {
			throw ex;
		}
	}
}
