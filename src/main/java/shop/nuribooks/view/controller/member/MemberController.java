package shop.nuribooks.view.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.dto.member.request.MemberCreateRequest;
import shop.nuribooks.view.service.member.MemberService;

@Controller()
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;

	/**
	 *
	 * @author : nunu
	 * @return register.html
	 */
	@GetMapping("/sign-up")
	public String registerForm() {

		return "register";
	}

	/**
	 *
	 * @author : nunu
	 * @param userRequest : member API 에서 응답받은 메시지
	 * @param bindingResult : 검증용
	 * @return : 검증 성공 및 memberService 에서 2xx code 받을 시 redirect:login
	 * 			 예외 발생 시 register.html
	 */
	@PostMapping("/sign-up")
	public String registerUser(
		@Valid @ModelAttribute MemberCreateRequest userRequest,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		memberService.registerUser(userRequest);
		return "redirect:/login";

	}
}
