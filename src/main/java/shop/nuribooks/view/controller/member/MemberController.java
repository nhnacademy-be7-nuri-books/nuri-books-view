package shop.nuribooks.view.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.dto.common.ResponseMessage;
import shop.nuribooks.view.dto.member.request.MemberCreateRequest;
import shop.nuribooks.view.service.member.MemberService;

@Controller()
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/sign-up")
	public String registerForm(Model model, @RequestParam(required = false) String error) {
		if (error != null) {
			model.addAttribute("error", error);
		}
		return "register";
	}

	@PostMapping("/sign-up")
	public String registerUser(
		@Valid @ModelAttribute MemberCreateRequest userRequest,
		BindingResult bindingResult,
		Model model
	) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		ResponseMessage respnese = memberService.registerUser(userRequest);

		return "redirect:/login";

	}
}
