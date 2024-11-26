package shop.nuribooks.view.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.auth.dto.request.NonMemberRequest;
import shop.nuribooks.view.auth.service.NonmemberService;
import shop.nuribooks.view.order.order.service.OrderService;

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
	public String doNonMember(RedirectAttributes redirectAttributes, NonMemberRequest loginRequest) {
		String returnMessage = nonmemberService.checkNonMember(loginRequest);
		if (returnMessage.startsWith(successMessageKey)) {
			redirectAttributes.addFlashAttribute(successMessageKey, "비회원 주문 목록을 불러옵니다.");
			Long customerId = Long.parseLong(returnMessage.split(" ")[1]);

			// TODO: orderService에서 customerId를 기준으로 주문 목록 조회
			redirectAttributes.addFlashAttribute("", customerId);
			// return "redirect:/non-member/orders/detail";
			return "redirect:/";
		} else {
			redirectAttributes.addFlashAttribute(errorMessageKey, "비회원 주문 목록 조회에 실패하였습니다.");
			return "redirect:/non-member";
		}
	}
}
