package shop.nuribooks.view.controller.member;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.dto.member.request.MemberCreateRequest;
import shop.nuribooks.view.service.member.MemberService;

/**
 *
 * @author : nuri
 * @d : 1.0
 */
@Controller()
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;

	/**
	 * 회원가입 GET
	 * @author : nunu
	 * @return register.html
	 */
	@GetMapping("/sign-up")
	public String registerForm() {

		return "register";
	}

	/**
	 * 회원가입 POST
	 * @param userRequest : member API 에서 응답받은 메시지
	 * @param bindingResult : 검증용
	 * @return : 검증 성공 및 memberService 에서 2xx code 받을 시 redirect:login
	 * 			 예외 발생 시 register.html
	 */
	@PostMapping("/sign-up")
	public void registerUser(
		HttpServletResponse response,
		@Valid @ModelAttribute MemberCreateRequest userRequest,
		BindingResult bindingResult
	) throws IOException {
		if (bindingResult.hasErrors()) {
			// todo : 화면단에 검증 에러를 띄우기 위해 addAttribute 로 입력한 데이터 가져오기..
			response.sendRedirect("redirect:/register");
		}

		memberService.registerUser(userRequest);
		response.sendRedirect("redirect:/login");
	}
}
