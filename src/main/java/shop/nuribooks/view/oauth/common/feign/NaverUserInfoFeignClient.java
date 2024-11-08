package shop.nuribooks.view.oauth.common.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naverUserInfoFeignClient", url = "https://openapi.naver.com/v1")
public interface NaverUserInfoFeignClient {
	@PostMapping("/nid/me")
	Map<String, Object> getUserInfo(
		@RequestHeader("Authorization") String accessToken
	);
}
