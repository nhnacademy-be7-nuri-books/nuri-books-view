package shop.nuribooks.view.oauth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.common.feign.PaycoTokenFeignClient;
import shop.nuribooks.view.oauth.common.feign.PaycoUserInfoFeignClient;
import shop.nuribooks.view.oauth.common.property.OAuth2ClientProperties;
import shop.nuribooks.view.oauth.dto.PaycoUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2Service {
	private final OAuth2ClientProperties oAuth2ClientProperties;
	private final PaycoTokenFeignClient paycoTokenFeignClient;
	private final PaycoUserInfoFeignClient paycoUserInfoFeignClient;

	public String getPaycoLoginFormUri() {
		return oAuth2ClientProperties.getProvider().getPayco().getAuthorizationUri()
			+ "&response_type=" + "code"
			+ "&client_id=" + oAuth2ClientProperties.getRegistration().getPayco().getClientId()
			+ "&redirect_uri=" + oAuth2ClientProperties.getRegistration().getPayco().getRedirectUri();
	}

	public Optional<PaycoUser> paycoLogin(String code) {
		Map<String, Object> tokenResponse = paycoTokenFeignClient.getToken(
			oAuth2ClientProperties.getRegistration().getPayco().getClientId(),
			oAuth2ClientProperties.getRegistration().getPayco().getClientSecret(),
			oAuth2ClientProperties.getRegistration().getPayco().getAuthorizationGrantType(),
			code
		);

		String accessToken = getPaycoAccessToken(tokenResponse);
		Map<String, Object> userResponse = paycoUserInfoFeignClient.getUserRequest(
			oAuth2ClientProperties.getRegistration().getPayco().getClientId(),
			accessToken
		);

		Optional<PaycoUser> paycoUser = null;
		if (isPaycoSuccessful(userResponse)) {
			paycoUser = Optional.of(getPaycoUser(userResponse));
		}
		return paycoUser;
	}

	private String getPaycoAccessToken(Map<String, Object> tokenResponse) {
		return tokenResponse.get("access_token").toString();
	}

	private PaycoUser getPaycoUser(Map<String, Object> userResponse) {
		Map<String, Object> member = (Map<String, Object>) ((Map<String, Object>)userResponse.get("data")).get("member");
		return new PaycoUser(member.get("idNo").toString(), member.get("email").toString());
	}

	private boolean isPaycoSuccessful(Map<String, Object> userResponse) {
		return ((Map<String, Object>)userResponse.get("header")).get("isSuccessful").toString().equalsIgnoreCase("true");
	}
}
