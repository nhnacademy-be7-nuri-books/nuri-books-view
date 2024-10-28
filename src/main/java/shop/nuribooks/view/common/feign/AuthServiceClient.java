package shop.nuribooks.view.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.auth.dto.request.LoginRequest;

/**
 * auth 관련 FeignClient
 * name : Eureka 에 등록된 서비스 이름
 * contextId : FeignClient 를 구분하기 위한 이름
 *
 * @author nuri
 */
@FeignClient(name = "gateway", contextId = "auth")
public interface AuthServiceClient {

	/**
	 * POST 로그인
	 *
	 * @param loginRequest 로그인 정보
	 * @return 응답 메시지
	 */
	@PostMapping("/api/auth/login")
	ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest);

	// todo 로그아웃 추가 예정
}

