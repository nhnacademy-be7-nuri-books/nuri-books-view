package shop.nuribooks.view.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import shop.nuribooks.view.auth.dto.request.LoginRequest;
import shop.nuribooks.view.auth.dto.request.NonMemberRequest;
import shop.nuribooks.view.auth.dto.response.NonMemberResponse;

/**
 * auth 관련 FeignClient
 * name : Eureka 에 등록된 서비스 이름
 * contextId : FeignClient 를 구분하기 위한 이름
 *
 * @author nuri
 */
@FeignClient(name = "auth", url = "http://localhost:8080")
public interface AuthServiceClient {

	/**
	 * POST 로그인
	 *
	 * @param loginRequest 로그인 정보
	 * @return 응답 메시지
	 */
	@Operation(summary = "로그인", description = "사용자의 로그인 요청을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공"),
		@ApiResponse(responseCode = "401", description = "인증 실패"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	@PostMapping("/api/auth/login")
	ResponseEntity<String> login(@RequestBody LoginRequest loginRequest);

	/**
	 * POST 로그아웃
	 *
	 * @return 응답 메세지
	 */
	@Operation(summary = "로그아웃", description = "사용자의 로그아웃 요청을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "401", description = "인증 실패"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	@PostMapping("/api/auth/logout")
	ResponseEntity<Void> logout();

	@PostMapping("/api/auth/non-member/check")
	ResponseEntity<NonMemberResponse> checkNonMember(
		@RequestBody NonMemberRequest nonMemberRequest
	);
}

