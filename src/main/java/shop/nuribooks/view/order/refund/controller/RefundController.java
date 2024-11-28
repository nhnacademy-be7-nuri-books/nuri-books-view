package shop.nuribooks.view.order.refund.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.order.refund.dto.request.RefundRequest;
import shop.nuribooks.view.order.refund.dto.response.RefundInfoResponse;
import shop.nuribooks.view.order.refund.service.RefundService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RefundController {

	private final RefundService refundService;

	@Value("${error.message-key}")
	private String errorMessageKey;

	@GetMapping("/orders/refunds/{order-id}")
	public String getRefundResponse(@PathVariable(name = "order-id") Long orderId,
		Model model, RedirectAttributes redirectAttributes) {
		try {
			RefundInfoResponse refundResponse = refundService.getRefundResponse(orderId);
			model.addAttribute("orderId", orderId);
			model.addAttribute("refundInfo", refundResponse.refundInfo());
		} catch (FeignException e) {
			redirectAttributes.addFlashAttribute(errorMessageKey, ExceptionUtil.handleFeignException(e));
			return "redirect:/orders/detail/" + orderId;
		}
		return "member/order/refund/refund";
	}

	@PostMapping("/orders/refunds/{order-id}")
	public String registerRefund(@PathVariable(name = "order-id") Long orderId,
		@ModelAttribute RefundRequest refundRequest) {
		refundService.registerRefund(orderId, refundRequest);

		return "redirect:/orders/list";
	}

}
