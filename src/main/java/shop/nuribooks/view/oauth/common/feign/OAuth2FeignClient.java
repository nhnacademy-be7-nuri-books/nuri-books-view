package shop.nuribooks.view.oauth.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.oauth.dto.OAuth2UserResponse;

@FeignClient(name = "oauth", url = "http://localhost:8080")
public interface OAuth2FeignClient {
	// OAuth2 로그인을 위한 API
	@PostMapping("/api/auth/oauth2")
	ResponseEntity<String> oauth2Login(@RequestBody OAuth2UserResponse oAuth2UserResponse);
}

