package shop.nuribooks.view.admin.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.admin.member.dto.response.MemberSearchResponse;

@FeignClient(name = "adminMember", url = "http://localhost:8080")
public interface AdminMemberServiceClient {

	/**
	 * 관리자가 다양한 검색 조건으로 회원 조회
	 */
	@GetMapping("/api/members")
	ResponseEntity<Page<MemberSearchResponse>> memberSearchWithPaging(
		@RequestParam(value = "name", required = false) String name,
		@RequestParam(value = "username", required = false) String username,
		@RequestParam(value = "email", required = false) String email,
		@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
		@RequestParam(value = "gender", required = false) String gender,
		@RequestParam(value = "gradeName", required = false) String gradeName,
		@RequestParam(value = "status", required = false) String status,
		@RequestParam(value = "authority", required = false) String authority,
		@RequestParam(value = "page") int page,
		@RequestParam(value = "size") int size);
}
