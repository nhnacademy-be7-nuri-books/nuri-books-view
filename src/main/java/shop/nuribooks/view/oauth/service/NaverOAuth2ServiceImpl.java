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
import shop.nuribooks.view.oauth.common.feign.NaverTokenFeignClient;
import shop.nuribooks.view.oauth.common.feign.NaverUserInfoFeignClient;
import shop.nuribooks.view.oauth.common.feign.OAuth2FeignClient;
import shop.nuribooks.view.oauth.common.property.OAuth2ClientProperties;
import shop.nuribooks.view.oauth.common.type.OAuth2ServicePrefix;
import shop.nuribooks.view.oauth.dto.OAuth2ResultResponse;
import shop.nuribooks.view.oauth.dto.OAuth2UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverOAuth2ServiceImpl implements OAuth2Service{
	private final OAuth2ClientProperties oAuth2ClientProperties;
	private final NaverTokenFeignClient naverTokenFeignClient;
	private final NaverUserInfoFeignClient naverUserInfoFeignClient;
	private final OAuth2FeignClient oAuth2FeignClient;

	@Override
	public String getLoginFormUri() {
		return oAuth2ClientProperties.getProvider().getNaver().getAuthorizationUri()
			+ "&response_type=" + "code"
			+ "&client_id=" + oAuth2ClientProperties.getRegistration().getNaver().getClientId()
			+ "&redirect_uri=" + oAuth2ClientProperties.getRegistration().getNaver().getRedirectUri();
	}

	@Override
	public OAuth2ResultResponse login(String code) {
		Map<String, Object> tokenResponse = naverTokenFeignClient.getToken(
			oAuth2ClientProperties.getRegistration().getNaver().getClientId(),
			oAuth2ClientProperties.getRegistration().getNaver().getClientSecret(),
			oAuth2ClientProperties.getRegistration().getNaver().getAuthorizationGrantType(),
			code
		);

		String accessToken = getAccessToken(tokenResponse);
		Map<String, Object> userResponse = naverUserInfoFeignClient.getUserInfo(
			"Bearer " + accessToken
		);

		Optional<OAuth2UserResponse> naverUser = Optional.of(getUserInfo(userResponse));
		naverUser.get().setId(OAuth2ServicePrefix.NAVER + naverUser.get().getId());

		// 로그인
		ResponseEntity<String> result = oAuth2FeignClient.oauth2Login(naverUser.get());

		if (result.getBody().equals("LOGIN_SUCCESS")) {
			Map<String, List<String>> responseMap = new HashMap<>();
			setTokenToClient(result, responseMap);
			responseMap.put("userInfo", List.of(naverUser.get().getId(), naverUser.get().getEmail()));
			return new OAuth2ResultResponse(responseMap, result.getBody());
		} else {
			Map<String, List<String>> responseMap = new HashMap<>();
			responseMap.put("userInfo", List.of(naverUser.get().getId(), naverUser.get().getEmail()));
			return new OAuth2ResultResponse(responseMap, result.getBody());
		}
	}

	private void setTokenToClient(ResponseEntity<String> result, Map<String, List<String>> responseMap) {
		HttpHeaders headers = result.getHeaders();

		// refresh jwt
		Optional.ofNullable(headers.get(HttpHeaders.SET_COOKIE))
			.filter(list -> !list.isEmpty())
			.ifPresent(setCookieHeaders -> responseMap.put(HttpHeaders.SET_COOKIE, setCookieHeaders));

		// accept jwt
		Optional.ofNullable(headers.get(HttpHeaders.AUTHORIZATION))
			.filter(list -> !list.isEmpty())
			.ifPresent(
				setAuthorizationHeaders -> responseMap.put(HttpHeaders.AUTHORIZATION, setAuthorizationHeaders));
	}

	private String getAccessToken(Map<String, Object> tokenResponse) {
		return tokenResponse.get("access_token").toString();
	}

	private OAuth2UserResponse getUserInfo(Map<String, Object> userResponse) {
		Map<String, Object> data = (Map<String, Object>)userResponse.get("response");
		return new OAuth2UserResponse(data.get("id").toString(), data.get("email").toString());
	}

	private boolean isOAuth2Successful(Map<String, Object> userResponse) {
		return userResponse.get("message").toString().equalsIgnoreCase("success");
	}
}

