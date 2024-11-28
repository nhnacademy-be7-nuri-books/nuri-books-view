package shop.nuribooks.view.order.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class NonMemberOrderController {
	@GetMapping("/non-member/orders")
	public String getNonMemberOrderList(
		@ModelAttribute("customerId") Long customerId,
		@ModelAttribute("email") String email,
		Model model
	) {
		model.addAttribute("customerId", customerId);
		model.addAttribute("email", email);
		return "nonmember/non-member-order-list";
	}
}
