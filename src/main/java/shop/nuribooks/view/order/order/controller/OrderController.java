package shop.nuribooks.view.order.order.controller;

import static shop.nuribooks.view.cart.controller.CartController.*;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
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
import shop.nuribooks.view.order.order.dto.OrderInformationResponse;
import shop.nuribooks.view.order.order.service.OrderService;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	@Value("${error.message-key}")
	private String errorMessageKey;

	private final OrderService orderService;

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
	public String getCartOrderForm(HttpServletRequest request, Model model) {
		String accessToken = CookieUtil.findByCookieKey(request, HttpHeaders.AUTHORIZATION);
		if (Objects.nonNull(accessToken)) {
			String cartId = JwtDecoder.getUserId(accessToken);
			OrderInformationResponse response = orderService.getCartOrderInformation(cartId);
			model.addAttribute("orderInformation", response);
			return "order/member-order-form";
		}
		String customerId = CookieUtil.findByCookieKey(request, CART_COOKIE_ID);
		if (Objects.nonNull(customerId)) {
			OrderInformationResponse response = orderService.getCartOrderInformation(customerId);
			model.addAttribute("orderInformation", response);
			return "order/customer-order-form";
		}
		return "cart";
	}
}
