package shop.nuribooks.view.order.order.controller;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.order.order.dto.request.OrderListPeriodRequest;
import shop.nuribooks.view.order.order.dto.response.OrderDetailResponse;
import shop.nuribooks.view.order.order.dto.response.OrderListResponse;
import shop.nuribooks.view.order.order.service.OrderService;

@RequiredArgsConstructor
@Controller
public class NonMemberOrderController {
	private final OrderService orderService;

	@GetMapping("/non-member/orders")
	public String getNonMemberOrderList(
		OrderListPeriodRequest orderListPeriodRequest,
		@RequestParam(name = "include-pending", defaultValue = "true") boolean includeOrdersInPendingStatus,
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "5") int size,
		HttpSession session,
		Model model
	) throws IOException {

		Long customerId = (Long)session.getAttribute("customerId");
		String email = (String)session.getAttribute("email");

		if (customerId == null || email == null) {
			return "redirect:/non-member";
		}

		if (customerId == null) {
			return "redirect:/non-member";
		}

		model.addAttribute("customerId", customerId);
		model.addAttribute("email", email);

		if (orderListPeriodRequest != null) {
			orderListPeriodRequest.setStart(orderListPeriodRequest.getStart());
			orderListPeriodRequest.setEnd(orderListPeriodRequest.getEnd());
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<OrderListResponse> orders = orderService.getNonMemberOrderList(orderListPeriodRequest,
			includeOrdersInPendingStatus,
			pageable,
			customerId);

		int pendingCount = 0;
		int paidCount = 0;
		int shippingCount = 0;
		int completedCount = 0;

		for (OrderListResponse order : orders) {
			String status = order.orderState().name();
			if ("PENDING".equals(status)) {
				pendingCount++;
			} else if ("PAID".equals(status)) {
				paidCount++;
			} else if ("DELIVERING".equals(status)) {
				shippingCount++;
			} else if ("COMPLETED".equals(status)) {
				completedCount++;
			}
		}

		model.addAttribute("pendingCount", pendingCount);
		model.addAttribute("paidCount", paidCount);
		model.addAttribute("shippingCount", shippingCount);
		model.addAttribute("completedCount", completedCount);
		model.addAttribute("pages", orders);
		model.addAttribute("period", orderListPeriodRequest);
		model.addAttribute("periodDuration", includeOrdersInPendingStatus);

		return "nonmember/non-member-order-list";
	}

	@GetMapping("/non-member/orders/detail/{order-id}")
	public String getNonMemberOrderDetails(@PathVariable("order-id") Long orderId, Model model,
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "5") int size,
		@RequestParam(required = false, defaultValue = "false") boolean isCancelled,
		HttpSession session) {

		Pageable pageable = PageRequest.of(page, size);
		Long customerId = (Long)session.getAttribute("customerId");
		OrderDetailResponse orderDetailResponse = orderService.getNonMemberOrderDetail(orderId, pageable, customerId);

		model.addAttribute("orderId", orderId);
		model.addAttribute("orderSummary", orderDetailResponse.order());
		model.addAttribute("pages", orderDetailResponse.orderItems());
		model.addAttribute("shipping", orderDetailResponse.shipping());

		BigDecimal unitPrice = orderDetailResponse.payment().unitPrice();
		BigDecimal shippingPrice = orderDetailResponse.payment().shippingPrice();

		BigDecimal middlePayment = (unitPrice != null ? unitPrice : BigDecimal.ZERO)
			.add(shippingPrice != null ? shippingPrice : BigDecimal.ZERO);

		model.addAttribute("payment", orderDetailResponse.payment());
		model.addAttribute("middlePayment", middlePayment);
		model.addAttribute("isCancelled", isCancelled);

		return "nonmember/non-member-order-detail";
	}
}

