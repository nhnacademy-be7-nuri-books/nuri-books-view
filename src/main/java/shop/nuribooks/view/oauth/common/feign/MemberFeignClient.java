package shop.nuribooks.view.oauth.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.oauth.dto.OAuth2RegisterRequest;
import shop.nuribooks.view.oauth.dto.OAuth2RegisterResponse;

@FeignClient(name = "oauth2Member", url = "http://localhost:8080")
public interface MemberFeignClient {
	@PostMapping("/api/member")
	ResponseEntity<OAuth2RegisterResponse> registerUser(@RequestBody OAuth2RegisterRequest registerRequest);
}
