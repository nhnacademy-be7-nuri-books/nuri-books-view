package shop.nuribooks.view.common.decoder;

import java.time.Instant;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.exception.CustomJsonProcessingException;

/**
 * access jwt 의 페이로드 값 decoder
 * * @author : nuri
 */
@Slf4j
public class JwtDecoder {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 페이로드 내 사용자 아이디 얻어오기 - PK
	 *
	 * @param accessToken 토큰값
	 * @return 토큰에서 추출한 사용자 아이디
	 */
	public static String getUserId(String accessToken) {
		return getClaimFromJwt(accessToken, "userId");
	}

	/**
	 * 페이로드 내 사용자 권한 얻어오기
	 *
	 * @param accessToken 토큰값
	 * @return 토큰에서 추출한 사용자 권한
	 */
	public static String getRole(String accessToken) {
		return getClaimFromJwt(accessToken, "role");
	}

	public static boolean isExpired(String accessToken) {
		try {
			long exp = Long.parseLong(getClaimFromJwt(accessToken, "exp"));
			Instant expirationInstant = Instant.ofEpochSecond(exp);
			Instant now = Instant.now();
			Instant checkTime = expirationInstant.minusSeconds(60 * 5);
			log.info("expiration : {}", expirationInstant);
			log.info("check time : {}", checkTime);
			log.info("now : {}", now);
			log.info("isExpired : {}", !now.isBefore(checkTime));
			return !now.isBefore(checkTime);
		} catch (Exception ex) {
			return true;
		}
	}

	/**
	 * 페이로드 json 값을 Map(키,값) 으로 변경 후 키 값으로 데이터를 얻어오기
	 *
	 * @param accessToken 토큰 값
	 * @param claimKey 키값
	 * @return 키에 해당되는 데이터
	 */
	private static String getClaimFromJwt(String accessToken, String claimKey) {
		String payloadJwt = accessToken.split("\\.")[1];
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(payloadJwt));

		try {
			Map<String, Object> jsonMap = objectMapper.readValue(payload, Map.class);

			if (jsonMap.containsKey(claimKey)) {
				return jsonMap.get(claimKey).toString();
			} else {
				throw new CustomJsonProcessingException(claimKey + " not found in the JWT payload");
			}
		} catch (JsonProcessingException e) {
			throw new CustomJsonProcessingException(e.getMessage());
		}
	}
}
