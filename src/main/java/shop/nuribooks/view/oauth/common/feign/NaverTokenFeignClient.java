package shop.nuribooks.view.oauth.common.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverTokenFeignClient", url = "https://nid.naver.com")
public interface NaverTokenFeignClient {
	@GetMapping("/oauth2.0/token")
	Map<String, Object> getToken(
		@RequestParam("client_id") String clientId,
		@RequestParam("client_secret") String clientSecret,
		@RequestParam("grant_type") String grantType,
		@RequestParam("code") String code
	);
}
