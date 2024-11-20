package shop.nuribooks.view.auth.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.auth.dto.request.AuthenticationCodeRequest;
import shop.nuribooks.view.auth.dto.request.LoginRequest;
import shop.nuribooks.view.auth.dto.request.MemberReactiveRequest;
import shop.nuribooks.view.cart.service.CartClientService;
import shop.nuribooks.view.common.feign.AuthServiceClient;
import shop.nuribooks.view.common.sender.MessageRequest;
import shop.nuribooks.view.common.sender.MessageSender;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.BadRequestException;
import shop.nuribooks.view.member.member.feign.MemberServiceClient;

/**
 * 인증 서비스 구현체
 *
 * @author : nuri
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	public static final String X_USER_ID = "X-USER-ID";

	private final AuthServiceClient authServiceClient;
	private final MemberServiceClient memberServiceClient;
	private final CartClientService cartClientService;
	private final MessageSender messageSender;

	@Value("${error.message-key}")
	private String errorMessageKey;
	@Value("${success.message-key}")
	private String successMessageKey;
	@Value("${info.authentication-code}")
	private String authenticationCode;

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
			ResponseEntity<String> response = authServiceClient.login(loginRequest);
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

			Optional.ofNullable(headers.get(X_USER_ID))
				.filter(list -> !list.isEmpty())
				.ifPresent(setCookieHeaders -> responseMap.put(X_USER_ID, setCookieHeaders));

			String userId = response.getHeaders().getFirst(X_USER_ID);
			if (Objects.nonNull(userId)) {
				cartClientService.loadCartToRedis(userId);
			}
			return responseMap;

		} catch (FeignException ex) {
			// 실제 메시지는 사용 안하고 서버 에러만 핸들링
			String message = ExceptionUtil.handleFeignException(ex);
			List<String> errorMessage = Collections.singletonList(message);
			responseMap.put(errorMessageKey, errorMessage);
			return responseMap;
		}
	}

	/**
	 * 로그아웃
	 */
	@Override
	public String logout() {
		try {
			ResponseEntity<Void> response = authServiceClient.logout();
			return successMessageKey;
		} catch (FeignException ex) {
			log.error("excepion :{}", ExceptionUtil.handleFeignException(ex));
			return ExceptionUtil.handleFeignException(ex);
		}
	}

	@Override
	public String sendAuthenticationCode(AuthenticationCodeRequest request) {
		//난수의 범위 111111 ~ 999999 (6자리 난수)
		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;

		authenticationCode = String.valueOf(checkNum);

		return messageSender.sendMessage(
			request, new MessageRequest("휴면 인증", "휴면 회원 인증번호 입니다: " + checkNum));
	}

	@Override
	public void reactiveMember(MemberReactiveRequest request) {

		if (!request.authenticationCode().equals(authenticationCode)) {
			// 인증번호가 일치하지 않음
			throw new BadRequestException("인증번호가 일치하지 않습니다.");
		}

		// TODO: 회원의 휴면 상태 해지 요청
		memberServiceClient.memberReactive(request.username());
	}
}
