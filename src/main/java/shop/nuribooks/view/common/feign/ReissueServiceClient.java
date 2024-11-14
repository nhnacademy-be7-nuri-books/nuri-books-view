package shop.nuribooks.view.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * auth 관련 FeignClient
 * name : Eureka 에 등록된 서비스 이름
 * contextId : FeignClient 를 구분하기 위한 이름
 *
 * @author nuri
 */
@FeignClient(name = "reissue", url = "http://localhost:8080")
public interface ReissueServiceClient {

	@PostMapping("/api/auth/reissue")
	ResponseEntity<String> reissue(@RequestBody String refreshToken);
}
