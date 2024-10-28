package shop.nuribooks.view.service.auth;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.feign.AuthServiceClient;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.dto.auth.request.LoginRequest;

/**
 * 인증 서비스 구현체
 *
 * @author : nuri
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthServiceClient authServiceClient;

	@Value("${error.message-key}")
	private String errorMessageKey;

	/**
	 * 로그인을 처리하는 메서드
	 *
	 * <p>
	 *     feignClient 로 응답을 받아와서 처리
	 * </p>
	 *
	 * @param loginRequest 등록할 회원의 정보 {@link LoginRequest}
	 * @return 헤더 정보 반환,
	 * 		   예외 발생 시 예외 메시지 반환
	 */
	public Map<String, List<String>> login(@Valid LoginRequest loginRequest) {

		Map<String, List<String>> responseMap = new HashMap<>();

		try {
			ResponseEntity<Void> response = authServiceClient.login(loginRequest);
			HttpHeaders headers = response.getHeaders();

			// refresh jwt
			Optional.ofNullable(headers.get(HttpHeaders.SET_COOKIE))
				.filter(list -> !list.isEmpty())
				.ifPresent(setCookieHeaders -> responseMap.put(HttpHeaders.SET_COOKIE, setCookieHeaders));

			// accept jwt
			Optional.ofNullable(headers.get(HttpHeaders.AUTHORIZATION))
				.filter(list -> !list.isEmpty())
				.ifPresent(
					setAuthorizationHeaders -> responseMap.put(HttpHeaders.AUTHORIZATION, setAuthorizationHeaders));

			return responseMap;

		} catch (FeignException ex) {
			List<String> errorMessage = Collections.singletonList(ExceptionUtil.handleFeignException(ex));
			responseMap.put(errorMessageKey, errorMessage);
			return responseMap;
		}
	}
}
