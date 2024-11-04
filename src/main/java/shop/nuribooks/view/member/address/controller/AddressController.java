package shop.nuribooks.view.member.address.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nuribooks.view.member.dto.request.MemberRegisterRequest;
import shop.nuribooks.view.member.service.MemberService;


@Controller()
@RequiredArgsConstructor
@Slf4j
public class AddressController {

	@Value("${error.message-key}")
	private String errorMessageKey;

	@Value("${success.message-key}")
	private String successMessageKey;


	@GetMapping("/address/register")
	public String registerForm() {
		return "member/address/address-register";
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
//	@PostMapping("/sign-up")
//	public String registerUser(
//		RedirectAttributes redirectAttributes,
//		@Valid @ModelAttribute MemberRegisterRequest userRequest
//	) {
//		String returnMessage = memberService.registerUser(userRequest);
//
//		if (returnMessage.startsWith(successMessageKey)) {
//			log.info("회원가입 성공");
//			redirectAttributes.addFlashAttribute(successMessageKey, "회원가입에 성공하였습니다.");
//			return "redirect:/login";
//		} else {
//			log.error("회원가입 실패");
//			log.error("user register failed: {}", returnMessage);
//			redirectAttributes.addFlashAttribute("userRequest", userRequest);
//			redirectAttributes.addFlashAttribute(errorMessageKey, returnMessage);
//			return "redirect:/sign-up";
//		}
//	}

}
