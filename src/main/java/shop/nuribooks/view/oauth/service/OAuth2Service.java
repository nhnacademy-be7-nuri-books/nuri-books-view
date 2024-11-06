package shop.nuribooks.view.oauth.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.common.feign.PaycoTokenFeignClient;
import shop.nuribooks.view.oauth.common.property.OAuth2ClientProperties;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2Service {
	private final OAuth2ClientProperties oAuth2ClientProperties;
	private final PaycoTokenFeignClient paycoTokenFeignClient;

	public String getPaycoLoginFormUri() {
		return oAuth2ClientProperties.getProvider().getPayco().getAuthorizationUri()
			+ "&response_type=" + "code"
			+ "&client_id=" + oAuth2ClientProperties.getRegistration().getPayco().getClientId()
			+ "&redirect_uri=" + oAuth2ClientProperties.getRegistration().getPayco().getRedirectUri();
	}

	public String paycoLogin(String code) {
		Map<String, Object> tokenResponse = paycoTokenFeignClient.getToken(
			oAuth2ClientProperties.getRegistration().getPayco().getClientId(),
			oAuth2ClientProperties.getRegistration().getPayco().getClientSecret(),
			oAuth2ClientProperties.getRegistration().getPayco().getAuthorizationGrantType(),
			code
		);

		String accessToken = getAccessToken(tokenResponse);
		
		return "ok";
	}

	public String getAccessToken(Map<String, Object> tokenResponse) {
		return tokenResponse.get("access_token").toString();
	}

}
