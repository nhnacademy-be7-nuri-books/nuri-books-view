package shop.nuribooks.view.common.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import shop.nuribooks.view.exception.member.MemberRegisterFailedException;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(MemberRegisterFailedException.class)
	public String handleMemberRegisterFailedException(MemberRegisterFailedException ex, Model model) {
		model.addAttribute("error", ex.getMessage());
		return "register";
	}
}
