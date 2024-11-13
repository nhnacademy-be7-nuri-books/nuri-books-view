package shop.nuribooks.view.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.member.dto.request.MemberRegisterRequest;
import shop.nuribooks.view.member.dto.request.MemberUpdateRequest;
import shop.nuribooks.view.member.dto.response.MemberDetailsResponse;
import shop.nuribooks.view.member.service.MemberService;

/**
 * 회원 관련 Controller
 *
 * <p>
 *     회원 관련 요청을 처리
 * </p>
 *
 * @author : nuri
 */
@Controller()
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@Value("${error.message-key}")
	private String errorMessageKey;

	@Value("${success.message-key}")
	private String successMessageKey;

	/**
	 * 회원가입 GET
	 *
	 * @return sign-up.html
	 */
	@Operation(summary = "회원가입 페이지", description = "회원가입 양식을 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원가입 페이지 반환 성공")
	})
	@GetMapping("/sign-up")
	public String registerForm() {
		return "member/sign-up";
	}

	/**
	 * 회원가입 POST
	 *
	 * <p>
	 * 회원 등록 정보를 받아 검증 후, 성공 시 로그인 페이지로 리다이렉트
	 * 실패 시 입력된 정보를 다시 회원가입 페이지로 리다이렉트 후 오류 메시지를 표시
	 * </p>
	 *
	 * @param redirectAttributes 리다이렉트 시 전달할 속성
	 * @param userRequest 검증을 위한 {@link MemberRegisterRequest} 객체로
	 *                    사용자가 입력한 회원 등록 정보를 포함
	 * @return 회원가입 성공 시 로그인 페이지로 리다이렉트하는 URL,
	 *         실패 시 회원가입 페이지로 리다이렉트하는 URL
	 */
	@Operation(summary = "회원가입", description = "사용자의 회원가입 요청을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "302", description = "회원가입 성공 및 로그인 페이지로 리다이렉트"),
		@ApiResponse(responseCode = "302", description = "회원가입 실패 및 회원가입 페이지로 리다이렉트"),
		@ApiResponse(responseCode = "500", description = "서버 오류 : 회원가입 실패")
	})
	@PostMapping("/sign-up")
	public String registerUser(
		RedirectAttributes redirectAttributes,
		@Valid @ModelAttribute MemberRegisterRequest userRequest
	) {
		String returnMessage = memberService.registerUser(userRequest);

		if (returnMessage.startsWith(successMessageKey)) {
			log.info("회원가입 성공");
			redirectAttributes.addFlashAttribute(successMessageKey, "회원가입에 성공하였습니다.");
			return "redirect:/login";
		} else {
			log.error("회원가입 실패");
			log.error("user register failed: {}", returnMessage);
			redirectAttributes.addFlashAttribute("userRequest", userRequest);
			redirectAttributes.addFlashAttribute(errorMessageKey, returnMessage);
			return "redirect:/sign-up";
		}
	}

	/**
	 * 마이 페이지 GET
	 * @return myPage.html
	 */
	@Operation(summary = "회원의 마이 페이지", description = "회원의 마이 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "마이 페이지 반환 성공")
	})
	@GetMapping("/myPage")
	public String myPage() {
		return "member/myPage";
	}

	/**
	 * 회원 정보 조회 페이지 GET
	 * @return myDetail.html
	 */
	@Operation(summary = "회원 정보 조회 페이지", description = "회원 정보 조회 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 정보 조회 페이지 반환 성공")
	})
	@GetMapping("/myDetail")
	public String myDetail(Model model) {

		MemberDetailsResponse member = memberService.getMemberDetails();

		model.addAttribute("member", member);

		return "member/myDetail";
	}

	/**
	 * 회원 정보 수정 페이지 GET
	 * @return myEdit.html
	 */
	@Operation(summary = "회원 정보 수정 페이지", description = "회원 정보 수정 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 정보 수정 페이지 반환 성공")
	})
	@GetMapping("/myEdit")
	public String myEdit(Model model) {

		MemberUpdateRequest memberUpdateRequest = memberService.getMemberDetailsBeforeUpdate();

		model.addAttribute("MemberUpdateRequest", memberUpdateRequest);

		return "member/myEdit";
	}

	/**
	 * 회원 정보 수정 진행
	 */
	@Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "302", description = "회원 정보 수정 후 회원 정보 페이지로 리다이렉트"),
		@ApiResponse(responseCode = "500", description = "서버 오류 : 회원 정보 수정 실패")
	})
	@PostMapping("/myEdit")
	public String memberUpdate(@Valid @ModelAttribute MemberUpdateRequest request) {

		memberService.memberUpdate(request);

		return "redirect:/myEdit";
	}


	/**
	 * 나의 장바구니 페이지 GET
	 * @return myCart.html
	 */
	@Operation(summary = "나의 장바구니 페이지", description = "나의 장바구니 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "나의 장바구니 페이지 반환 성공")
	})
	@GetMapping("/myCart")
	public String myCart() {
		return "member/myCart";
	}

	/**
	 * 나의 구매내역 페이지 GET
	 * @return myOrders.html
	 */
	@Operation(summary = "나의 구매내역 페이지", description = "나의 구매내역 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "나의 구매내역 페이지 반환 성공")
	})
	@GetMapping("/myOrders")
	public String myOrders() {
		return "member/myOrders";
	}

	/**
	 * 나의 쿠폰 페이지 GET
	 * @return myCoupons.html
	 */
	@Operation(summary = "나의 쿠폰 페이지", description = "나의 쿠폰 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "나의 쿠폰 페이지 반환 성공")
	})
	@GetMapping("/myCoupons")
	public String myCoupons() {
		return "member/myCoupons";
	}

	/**
	 * 회원 탈퇴 페이지 GET
	 * @return myGoodbye.html
	 */
	@Operation(summary = "회원 탈퇴 페이지", description = "회원 탈퇴 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 탈퇴 페이지 반환 성공")
	})
	@GetMapping("/myGoodbye")
	public String myGoodbye(Model model) {

		Integer point = memberService.getMemberDetailsBeforeWithdraw();
		model.addAttribute("point", point);

		return "member/myGoodbye";
	}

	/**
	 * 회원 탈퇴 진행
	 */
	@Operation(summary = "회원 탈퇴 진행", description = "회원 탈퇴를 진행합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 탈퇴가 성공적으로 진행되었습니다."),
		@ApiResponse(responseCode = "500", description = "서버 오류 : 회원 탈퇴 실패")
	})
	@PostMapping("/myGoodbye")
	public String memberWithdraw() {



		return "redirect:/";
	}
}
