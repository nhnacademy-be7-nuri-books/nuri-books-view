package shop.nuribooks.view.order.refund.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.order.refund.dto.request.RefundRequest;
import shop.nuribooks.view.order.refund.dto.response.RefundInfoResponse;
import shop.nuribooks.view.order.refund.service.RefundService;

@RequiredArgsConstructor
@Controller
public class RefundController {

	private final RefundService refundService;

	@GetMapping("/orders/refunds/{order-id}")
	public String getRefundResponse(@PathVariable(name = "order-id") Long orderId,
		Model model) {
		RefundInfoResponse refundResponse = refundService.getRefundResponse(orderId);

		model.addAttribute("orderId", orderId);
		model.addAttribute("refundInfo", refundResponse.refundInfo());

		return "member/order/refund/refund";
	}

	@PostMapping("/orders/refunds/{order-id}")
	public String registerRefund(@PathVariable(name = "order-id") Long orderId,
		@ModelAttribute RefundRequest refundRequest) {
		refundService.registerRefund(orderId, refundRequest);

		return "redirect:/orders/list";
	}

}
