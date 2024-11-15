package shop.nuribooks.view.admin.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.member.dto.request.MemberSearchRequest;
import shop.nuribooks.view.admin.member.dto.response.MemberSearchResponse;
import shop.nuribooks.view.admin.member.service.AdminMemberService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminMemberController {

	private final AdminMemberService adminMemberService;

	/**
	 * 회원 관리 GET
	 * @return admin/member.html
	 */
	@Operation(summary = "회원 관리", description = "회원 관리 페이지를 반환합니다.")
	@GetMapping("/admin/member")
	public String adminMember() {
		return "admin/member";
	}

	/**
	 * 다양한 검색 조건들로 회원 검색
	 */
	@GetMapping("/admin/member/members")
	public String memberSearchWithPaging(@ModelAttribute MemberSearchRequest request, Model model) {

		Page<MemberSearchResponse> result = adminMemberService.memberSearchWithPaging(request);

		model.addAttribute("members", result);

		return "admin/member";
	}
}
