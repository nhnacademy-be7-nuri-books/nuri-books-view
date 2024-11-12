package shop.nuribooks.view.admin.controller;

import java.io.IOException;
import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.dto.request.MemberSearchRequest;
import shop.nuribooks.view.admin.dto.response.MemberSearchResponse;
import shop.nuribooks.view.member.feign.MemberServiceClient;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

	private final MemberServiceClient memberServiceClient;

	/**
	 * admin GET
	 *
	 * @return admin.html
	 */
	@Operation(summary = "관리자 페이지", description = "관리자 페이지를 반환합니다.")
	@GetMapping("/admin")
	public String adminHome() throws IOException {
		return "admin/admin";
	}

	/**
	 * 회원 관리 GET
	 *
	 * @return admin/member.html
	 */
	@Operation(summary = "회원 관리", description = "회원 관리 페이지를 반환합니다.")
	@GetMapping("/admin/member")
	public String adminMember() {
		return "admin/member";
	}

	@GetMapping("/admin/member/members")
	public String memberSearchWithPaging(@ModelAttribute MemberSearchRequest request, Model model) {

		// Feign Client 호출
		ResponseEntity<Page<MemberSearchResponse>> response = memberServiceClient.memberSearchWithPaging(
			request.name(),
			request.email(),
			request.phoneNumber(),
			request.gender(),
			request.status(),
			request.page(),
			request.size()
		);

		if (response.getBody() != null) {
			model.addAttribute("members", response.getBody().getContent());
		} else {
			model.addAttribute("members", Collections.emptyList());  // 빈 리스트를 사용
		}

		return "admin/member";
	}
}
