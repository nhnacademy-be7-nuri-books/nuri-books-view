package shop.nuribooks.view.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.auth.dto.request.NonMemberRequest;
import shop.nuribooks.view.auth.service.NonmemberService;
import shop.nuribooks.view.order.order.service.OrderService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NomMemberController {
	private final NonmemberService nonmemberService;
	private final OrderService orderService;

	@Value("${success.message-key}")
	private String successMessageKey;

	@Value("${error.message-key}")
	private String errorMessageKey;

	@GetMapping("/non-member")
	public String nomMember() {
		return "auth/non-login";
	}

	@PostMapping("/non-member")
	public String doNonMember(HttpSession session, RedirectAttributes redirectAttributes,
		NonMemberRequest loginRequest) {
		String returnMessage = nonmemberService.checkNonMember(loginRequest);
		if (returnMessage.startsWith(successMessageKey)) {
			redirectAttributes.addFlashAttribute(successMessageKey, "비회원 주문 목록을 불러옵니다.");
			Long customerId = Long.parseLong(returnMessage.split(" ")[1]);
			String email = returnMessage.split(" ")[2];

			// 1. redirectAttributes 사용
			// redirectAttributes.addFlashAttribute("customerId", customerId);
			// redirectAttributes.addFlashAttribute("email", email);

			// 2. session 사용
			session.setAttribute("customerId", customerId);
			session.setAttribute("email", email);

			return "redirect:/non-member/orders";
		} else {
			redirectAttributes.addFlashAttribute(errorMessageKey, "비회원 주문 목록 조회에 실패하였습니다.");
			return "redirect:/non-member";
		}
	}
}
