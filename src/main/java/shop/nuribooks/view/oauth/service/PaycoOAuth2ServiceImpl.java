package shop.nuribooks.view.oauth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.common.feign.PaycoTokenFeignClient;
import shop.nuribooks.view.oauth.common.feign.PaycoUserInfoFeignClient;
import shop.nuribooks.view.oauth.common.property.OAuth2ClientProperties;
import shop.nuribooks.view.oauth.dto.OAuth2ResultResponse;
import shop.nuribooks.view.oauth.dto.OAuth2UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaycoOAuth2ServiceImpl implements OAuth2Service{
	private final OAuth2ClientProperties oAuth2ClientProperties;
	private final PaycoTokenFeignClient paycoTokenFeignClient;
	private final PaycoUserInfoFeignClient paycoUserInfoFeignClient;

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

		Optional<OAuth2UserResponse> paycoUser = null;
		if (isOAuth2Successful(userResponse)) {
			paycoUser = Optional.of(getUserInfo(userResponse));
		}

		log.info("payco : {}", paycoUser);
		return null;
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

	private boolean isOAuth2Successful(Map<String, Object> userResponse) {
		return ((Map<String, Object>)userResponse.get("header")).get("isSuccessful").toString().equalsIgnoreCase("true");
	}
}
