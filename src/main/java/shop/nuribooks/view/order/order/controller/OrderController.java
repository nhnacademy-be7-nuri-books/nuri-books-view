package shop.nuribooks.view.order.order.controller;

import static shop.nuribooks.view.cart.controller.CartController.*;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.common.decoder.JwtDecoder;
import shop.nuribooks.view.common.util.CookieUtil;
import shop.nuribooks.view.order.order.dto.request.OrderListPeriodRequest;
import shop.nuribooks.view.order.order.dto.response.OrderDetailResponse;
import shop.nuribooks.view.order.order.dto.response.OrderInformationResponse;
import shop.nuribooks.view.order.order.dto.response.OrderListResponse;
import shop.nuribooks.view.order.order.service.OrderService;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	@Value("${error.message-key}")
	private String errorMessageKey;

	/**
	 * 상품 바로 구매 - 주문 폼 불러오기
	 *
	 * @param request HttpServletRequest
	 * @param bookId 책 아이디
	 * @param quantity 책 갯수
	 * @param attributes
	 * @param model
	 * @return
	 */
	@GetMapping("/{book-id}")
	public String getOrderForm(
		HttpServletRequest request, @PathVariable("book-id") Long bookId, @RequestParam Integer quantity,
		RedirectAttributes attributes, Model model) {

		String accessToken = CookieUtil.findByCookieKey(request, HttpHeaders.AUTHORIZATION);

		OrderInformationResponse response = orderService.getOrderInformation(bookId, quantity);

		model.addAttribute("orderInformation", response);

		if (Objects.nonNull(accessToken)) {
			// 회원
			return "order/member-order-form";
		} else {
			// 비회원
			return "order/customer-order-form";
		}
	}

	@GetMapping("/cart")
	public String getCartOrderForm(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String accessToken = CookieUtil.findByCookieKey(request, HttpHeaders.AUTHORIZATION);
		if (Objects.nonNull(accessToken)) {
			String cartId = JwtDecoder.getUserId(accessToken);
			OrderInformationResponse response = orderService.getCartOrderInformation(cartId);
			if (response.bookOrderResponse().isEmpty()) {
				redirectAttributes.addFlashAttribute(errorMessageKey, "도서를 담아주세요");
				return "redirect:/api/cart";
			}
			model.addAttribute("orderInformation", response);
			return "order/member-order-form";
		}
		String customerId = CookieUtil.findByCookieKey(request, CART_COOKIE_ID);
		if (Objects.nonNull(customerId)) {
			OrderInformationResponse response = orderService.getCartOrderInformation(customerId);
			if (response.bookOrderResponse().isEmpty()) {
				redirectAttributes.addFlashAttribute(errorMessageKey, "도서를 담아주세요");
				return "redirect:/api/cart";
			}
			model.addAttribute("orderInformation", response);
			return "order/customer-order-form";
		}
		return "cart";
	}

	@GetMapping("/list")
	public String getOrderList(Model model, OrderListPeriodRequest orderListPeriodRequest,
		@RequestParam(name = "include-pending", defaultValue = "true") boolean includeOrdersInPendingStatus,
		Pageable pageable) throws
		IOException {
		Page<OrderListResponse> orders = orderService.getOrderList(orderListPeriodRequest, includeOrdersInPendingStatus,
			pageable);

		int pendingCount = 0;
		int paidCount = 0;
		int shippingCount = 0;
		int completedCount = 0;

		for (OrderListResponse order : orders) {
			String status = order.orderState().name();  // 상태를 가져옵니다.

			// 상태에 따라 카운트를 증가시킵니다.
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

		return "member/order/my-orders";
	}

	@GetMapping("/detail/{order-id}")
	public String getOrderDetail(@PathVariable("order-id") Long orderId, Model model) {

		OrderDetailResponse orderDetailResponse = orderService.getOrderDetail(orderId);

		model.addAttribute("orderId", orderId);
		model.addAttribute("orderSummary", orderDetailResponse.order());
		model.addAttribute("pages", orderDetailResponse.orderItems());
		model.addAttribute("shipping", orderDetailResponse.shipping());
		model.addAttribute("payment", orderDetailResponse.payment());

		return "member/order/order-detail";
	}

}
