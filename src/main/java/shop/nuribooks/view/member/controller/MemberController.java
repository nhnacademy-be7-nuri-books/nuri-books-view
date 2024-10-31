package shop.nuribooks.view.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.member.dto.request.MemberRegisterRequest;
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

	/**
	 * 회원가입 GET
	 *
	 * @return register.html
	 */
	@GetMapping("/sign-up")
	public String registerForm() {
		return "register";
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
	@PostMapping("/sign-up")
	public String registerUser(
		RedirectAttributes redirectAttributes,
		@Valid @ModelAttribute MemberRegisterRequest userRequest
	) {

		String returnMessage = memberService.registerUser(userRequest);
		ModelAndView modelAndView = new ModelAndView();

		if (returnMessage.startsWith("Success")) {
			return "redirect:/login";
		} else {
			log.error("user register failed: {}", returnMessage);
			redirectAttributes.addFlashAttribute("userRequest", userRequest);
			redirectAttributes.addFlashAttribute(errorMessageKey, returnMessage);
			return "redirect:/sign-up";
		}
	}

}
