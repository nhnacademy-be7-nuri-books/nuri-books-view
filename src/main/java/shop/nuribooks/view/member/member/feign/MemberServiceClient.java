package shop.nuribooks.view.member.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.member.member.dto.request.MemberRegisterRequest;
import shop.nuribooks.view.member.member.dto.request.MemberPasswordUpdateRequest;
import shop.nuribooks.view.member.member.dto.response.MemberDetailsResponse;
import shop.nuribooks.view.member.member.dto.response.MemberRegisterResponse;

/**
 * member 관련 FeignClient
 *
 * <p>
 *    부가 설명
 * 	   name : Eureka 에 등록된 서비스 이름
 *     contextId : FeignClient 를 구분하기 위한 이름
 * </p>
 *
 * @author nuri
 */
@FeignClient(name = "member", url = "http://localhost:8080")
public interface MemberServiceClient {

	/**
	 * POST 회원 등록
	 *
	 * @param userRequest 회원 등록 정보 {@link MemberRegisterRequest}
	 * @return 응답 메시지 {@link MemberRegisterResponse},
	 * 		   예외 발생 시 feignException 으로 처리
	 */
	@PostMapping("/api/members")
	ResponseEntity<MemberRegisterResponse> registerUser(@RequestBody MemberRegisterRequest userRequest);

	/**
	 * 회원 정보 수정
	 */
	@PutMapping("/api/members/me")
	ResponseEntity<ResponseMessage> memberPasswordUpdate(@RequestBody MemberPasswordUpdateRequest request);

	/**
	 * 회원 정보 조회
	 */
	@GetMapping("/api/members/me")
	ResponseEntity<MemberDetailsResponse> getMemberDetails();

	/**
	 * 회원 탈퇴
	 */
	@DeleteMapping("/api/members/me")
	ResponseEntity<ResponseMessage> memberWithdraw();

	/**
	 * 회원의 최근 로그인 시간을 업데이트
	 */
	@PostMapping("/api/members/me/login-time")
	ResponseEntity<ResponseMessage> memberLatestLoginAtUpdate();
}
