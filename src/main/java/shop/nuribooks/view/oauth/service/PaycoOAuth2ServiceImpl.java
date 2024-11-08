package shop.nuribooks.view.oauth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.common.feign.OAuth2FeignClient;
import shop.nuribooks.view.oauth.common.feign.PaycoTokenFeignClient;
import shop.nuribooks.view.oauth.common.feign.PaycoUserInfoFeignClient;
import shop.nuribooks.view.oauth.common.property.OAuth2ClientProperties;
import shop.nuribooks.view.oauth.common.type.OAuth2ServicePrefix;
import shop.nuribooks.view.oauth.common.type.OAuth2Status;
import shop.nuribooks.view.oauth.dto.OAuth2ResultResponse;
import shop.nuribooks.view.oauth.dto.OAuth2UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaycoOAuth2ServiceImpl implements OAuth2Service{
	private final OAuth2ClientProperties oAuth2ClientProperties;
	private final PaycoTokenFeignClient paycoTokenFeignClient;
	private final PaycoUserInfoFeignClient paycoUserInfoFeignClient;
	private final OAuth2FeignClient oAuth2FeignClient;

	@Override
	public String getLoginFormUri() {
		return oAuth2ClientProperties.getProvider().getPayco().getAuthorizationUri()
			+ "&response_type=" + "code"
			+ "&client_id=" + oAuth2ClientProperties.getRegistration().getPayco().getClientId()
			+ "&redirect_uri=" + oAuth2ClientProperties.getRegistration().getPayco().getRedirectUri();
	}

	@Override
	public OAuth2ResultResponse login(String code) {
		Map<String, Object> tokenResponse = paycoTokenFeignClient.getToken(
			oAuth2ClientProperties.getRegistration().getPayco().getClientId(),
			oAuth2ClientProperties.getRegistration().getPayco().getClientSecret(),
			oAuth2ClientProperties.getRegistration().getPayco().getAuthorizationGrantType(),
			code
		);

		String accessToken = getAccessToken(tokenResponse);
		Map<String, Object> userResponse = paycoUserInfoFeignClient.getUserInfo(
			oAuth2ClientProperties.getRegistration().getPayco().getClientId(),
			accessToken
		);

		Optional<OAuth2UserResponse> paycoUser = Optional.of(getUserInfo(userResponse));
		paycoUser.get().setId(OAuth2ServicePrefix.PAYCO + paycoUser.get().getId());

		// 로그인
		ResponseEntity<String> result = oAuth2FeignClient.oauth2Login(paycoUser.get());
		Map<String, List<String>> responseMap = new HashMap<>();
		responseMap.put("userInfo", List.of(paycoUser.get().getId(), paycoUser.get().getEmail()));
		if (result.getBody().equals(OAuth2Status.LOGIN_SUCCESS.toString())) {
			setTokenToClient(result, responseMap);
		}
		return new OAuth2ResultResponse(responseMap, result.getBody());
	}

	private void setTokenToClient(ResponseEntity<String> result, Map<String, List<String>> responseMap) {
		HttpHeaders headers = result.getHeaders();
		Optional.ofNullable(headers.get(HttpHeaders.SET_COOKIE))
			.filter(list -> !list.isEmpty())
			.ifPresent(setCookieHeaders -> responseMap.put(HttpHeaders.SET_COOKIE, setCookieHeaders));

		Optional.ofNullable(headers.get(HttpHeaders.AUTHORIZATION))
			.filter(list -> !list.isEmpty())
			.ifPresent(
				setAuthorizationHeaders -> responseMap.put(HttpHeaders.AUTHORIZATION, setAuthorizationHeaders));
	}

	private String getAccessToken(Map<String, Object> tokenResponse) {
		return tokenResponse.get("access_token").toString();
	}

	private OAuth2UserResponse getUserInfo(Map<String, Object> userResponse) {
		Map<String, Object> member = Optional.ofNullable(userResponse)
			.map(data -> (Map<String, Object>) data.get("data"))
			.map(dataMap -> (Map<String, Object>) dataMap.get("member"))
			.orElse(null);
		return member != null ? new OAuth2UserResponse(member.get("idNo").toString(), member.get("email").toString()) : null;
	}
}
