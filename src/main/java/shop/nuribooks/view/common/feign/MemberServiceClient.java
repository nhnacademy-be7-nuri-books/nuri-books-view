package shop.nuribooks.view.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.dto.member.request.MemberRegisterRequest;
import shop.nuribooks.view.dto.member.response.MemberRegisterResponse;

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
@FeignClient(name = "gateway", contextId = "member")
public interface MemberServiceClient {

	/**
	 * POST 회원 등록
	 *
	 * @param userRequest 회원 등록 정보 {@link MemberRegisterRequest}
	 * @return 응답 메시지 {@link MemberRegisterResponse},
	 * 		   예외 발생 시 feignException 으로 처리
	 */
	@PostMapping("/api/member")
	ResponseEntity<MemberRegisterResponse> registerUser(@RequestBody MemberRegisterRequest userRequest);

	// todo : 회원 관련 추가
}
