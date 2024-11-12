package shop.nuribooks.view.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.member.dto.request.MemberRegisterRequest;
import shop.nuribooks.view.member.dto.request.MemberUpdateRequest;
import shop.nuribooks.view.member.dto.response.MemberDetailsResponse;
import shop.nuribooks.view.member.dto.response.MemberRegisterResponse;
import shop.nuribooks.view.admin.dto.response.MemberSearchResponse;
import org.springframework.data.domain.Page;

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
	 * 관리자가 다양한 검색 조건으로 회원 조회
	 */
	@GetMapping("/api/members")
	ResponseEntity<Page<MemberSearchResponse>> memberSearchWithPaging(
		@RequestParam(value = "name", required = false) String name,
		@RequestParam(value = "email", required = false) String email,
		@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
		@RequestParam(value = "gender", required = false) String gender,
		@RequestParam(value = "status", required = false) String status,
		@RequestParam(value = "page") int page,
		@RequestParam(value = "size") int size);

	/**
	 * 회원 정보 수정
	 */
	@PatchMapping("/api/members/me")
	ResponseEntity<ResponseMessage> memberUpdate(@RequestBody MemberUpdateRequest request);

	/**
	 * 회원 정보 조회
	 */
	@GetMapping("/api/members/me")
	ResponseEntity<MemberDetailsResponse> getMemberDetails();
}
