package shop.nuribooks.view.oauth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.common.feign.NaverTokenFeignClient;
import shop.nuribooks.view.oauth.common.feign.NaverUserInfoFeignClient;
import shop.nuribooks.view.oauth.common.property.OAuth2ClientProperties;
import shop.nuribooks.view.oauth.dto.OAuth2UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverOAuth2ServiceImpl implements OAuth2Service{
	private final OAuth2ClientProperties oAuth2ClientProperties;
	private final NaverTokenFeignClient naverTokenFeignClient;
	private final NaverUserInfoFeignClient naverUserInfoFeignClient;

	@Override
	public String getLoginFormUri() {
		return oAuth2ClientProperties.getProvider().getNaver().getAuthorizationUri()
			+ "&response_type=" + "code"
			+ "&client_id=" + oAuth2ClientProperties.getRegistration().getNaver().getClientId()
			+ "&redirect_uri=" + oAuth2ClientProperties.getRegistration().getNaver().getRedirectUri();
	}

	@Override
	public Optional<OAuth2UserResponse> login(String code) {
		Map<String, Object> tokenResponse = naverTokenFeignClient.getToken(
			oAuth2ClientProperties.getRegistration().getNaver().getClientId(),
			oAuth2ClientProperties.getRegistration().getNaver().getClientSecret(),
			oAuth2ClientProperties.getRegistration().getNaver().getAuthorizationGrantType(),
			code
		);

		String accessToken = getAccessToken(tokenResponse);
		Map<String, Object> userResponse = naverUserInfoFeignClient.getUserInfo(
			tokenResponse.get("token_type") + " " + accessToken
		);

		Optional<OAuth2UserResponse> naverUser = null;
		if (isOAuth2Successful(userResponse)) {
			naverUser = Optional.of(getUserInfo(userResponse));
		}

		log.info("naver : {}", naverUser);
		return naverUser;
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

