package shop.nuribooks.view.oauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.oauth.dto.OAuth2RegisterRequest;
import shop.nuribooks.view.oauth.service.OAuth2MemberService;

@RequiredArgsConstructor
@Controller
public class OAuth2MemberController {
	private final OAuth2MemberService oAuth2MemberService;
	@Value("${error.message-key}")
	private String errorMessageKey;

	@Value("${success.message-key}")
	private String successMessageKey;

	@GetMapping("/simple-signup")
	public String simpleSignupForm(@ModelAttribute("id") String id, @ModelAttribute("email") String email,
		Model model) {
		model.addAttribute("id", id);
		model.addAttribute("email", email);
		return "member/simple-signup";
	}

	@PostMapping("/simple-signup")
	public String simpleSignup(@ModelAttribute OAuth2RegisterRequest registerRequest,
		RedirectAttributes redirectAttributes) {
		String returnMessage = oAuth2MemberService.signup(registerRequest);
		if (returnMessage.startsWith(successMessageKey)) {
			redirectAttributes.addFlashAttribute(successMessageKey, "회원가입에 성공하였습니다.");
			return "redirect:/login";
		} else {
			redirectAttributes.addFlashAttribute("userRequest", registerRequest);
			redirectAttributes.addFlashAttribute(errorMessageKey, "회원가입에 실패하였습니다.");
			return "redirect:/simple-signup";
		}
	}
}
