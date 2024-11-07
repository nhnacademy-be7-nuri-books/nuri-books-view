package shop.nuribooks.view.common.util;


import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.property.JwtProperties;

@Slf4j
@Component
public class JwtUtils {
	public static final Long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 200L;
	public static final Long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 1000L * 24;
	private final SecretKey secretKey;
	private final JwtProperties jwtProperties;

	public JwtUtils(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	public String getTokenType(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("tokenType", String.class);
		} catch (Exception ex) {
			log.info("TokenType을 가져오는데 실패하였습니다.");
		}
		return null;
	}

	public String getUserId(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", String.class);
		} catch (Exception ex) {
			log.info("UserId을 가져오는데 실패하였습니다.");
		}
		return null;
	}

	public String getRole(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
		} catch (Exception ex) {
			log.info("Role을 가져오는데 실패하였습니다.");
		}
		return null;
	}

	public Boolean isExpired(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
		} catch (Exception ex) {
			log.info("만료기한을 가져오는데 실패하였습니다.");
		}
		return true;
	}

	public String createJwt(String tokenType, String userId, String role, Long expiredMs) {
		return Jwts.builder()
			.claim("tokenType", tokenType)
			.claim("userId", userId)
			.claim("role", role)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.claim("issuer", jwtProperties.getIssuer())
			.signWith(secretKey)
			.compact();
	}
}
