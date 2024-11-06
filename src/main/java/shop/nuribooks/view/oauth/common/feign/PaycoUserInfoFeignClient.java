package shop.nuribooks.view.oauth.common.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paycoUserInfoFeignClient", url = "https://apis-payco.krp.toastoven.net")
public interface PaycoUserInfoFeignClient {
	@PostMapping("/payco/friends/find_member_v2.json")
	Map<String, Object> getUserRequest(
		@RequestHeader("client_id") String clientId,
		@RequestHeader("access_token") String accessToken
	);
}
