package shop.nuribooks.view.cart.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import shop.nuribooks.view.cart.dto.request.CartAddRequest;
import shop.nuribooks.view.cart.dto.request.CartRequestToServer;
import shop.nuribooks.view.cart.dto.request.CartUpdateRequest;
import shop.nuribooks.view.cart.dto.response.CartBookResponse;
import shop.nuribooks.view.cart.service.CartClientService;
import shop.nuribooks.view.common.decoder.JwtDecoder;

@RequiredArgsConstructor
@Controller
public class CartController {

	public static final String CART_COOKIE_ID = "cart-id";
	private final CartClientService cartClientService;

	@Value("${error.message-key}")
	private String errorMessageKey;
	@Value("${header.refresh-key-name}")
	private String refreshHeaderName;
	@Value("${success.message-key}")
	private String successMessageKey;

	@PostMapping("/api/cart")
	public String addCart(@ModelAttribute CartAddRequest cartAddRequest, HttpServletRequest request,
		HttpServletResponse response, RedirectAttributes redirectAttributes) {

		String cartId;
		Optional<String> memberId = Optional.empty();
		Optional<String> customerId = Optional.empty();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
					memberId = Optional.of(JwtDecoder.getUserId(cookie.getValue()));
				}
				if (cookie.getName().equals(CART_COOKIE_ID)) {
					customerId = Optional.of(cookie.getValue());
				}
			}
		}
		if (memberId.isPresent()) {
			cartId = memberId.get();
		}
		else if (customerId.isPresent()) {
			cartId = customerId.get();
		} else {
			cartId = UUID.randomUUID().toString();
			Cookie cookie = new Cookie("cart-id", cartId);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
		}
		CartRequestToServer cartAddRequestToServer = new CartRequestToServer(cartId,
			cartAddRequest.bookId(), cartAddRequest.quantity());
		cartClientService.addCart(cartAddRequestToServer);
		redirectAttributes.addFlashAttribute(successMessageKey, "상품이 장바구니에 담겼습니다.");
		return "redirect:/view/book/details/" + cartAddRequest.bookId();
	}

	@GetMapping("/api/cart")
	public String getCart(HttpServletRequest request, Model model) {
		String cartId;
		Optional<String> memberId = Optional.empty();
		Optional<String> customerId = Optional.empty();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// 회원인 경우
				if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
					memberId = Optional.of(JwtDecoder.getUserId(cookie.getValue()));
				}
				// 비회원인데 카트가 이미 있는 경우
				if (cookie.getName().equals(CART_COOKIE_ID)) {
					customerId = Optional.of(cookie.getValue());
				}
			}
		}
		if (memberId.isPresent()) {
			cartId = memberId.get();
		}
		else if (customerId.isPresent()) {
			cartId = customerId.get();
		} else {
			return "cart";
		}
		List<CartBookResponse> cart = cartClientService.getCart(cartId);
		BigDecimal totalPrice = getTotalPrice(cart);
		model.addAttribute("cartBookResponseList", cart);
		model.addAttribute("totalPrice", totalPrice);
		return "cart";
	}

	@PostMapping("/api/updateQuantity")
	public String updateCart(@ModelAttribute CartUpdateRequest cartUpdateRequest, HttpServletRequest request,
		HttpServletResponse response, RedirectAttributes redirectAttributes) {

		String cartId;
		Optional<String> memberId = Optional.empty();
		Optional<String> customerId = Optional.empty();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
					memberId = Optional.of(JwtDecoder.getUserId(cookie.getValue()));
				}
				if (cookie.getName().equals(CART_COOKIE_ID)) {
					customerId = Optional.of(cookie.getValue());
				}
			}
		}
		if (memberId.isPresent()) {
			cartId = memberId.get();
		}
		else if (customerId.isPresent()) {
			cartId = customerId.get();
		} else {
			return "cart";
		}
		CartRequestToServer cartRequestToServer = new CartRequestToServer(cartId,
			cartUpdateRequest.bookId(), cartUpdateRequest.quantity());
		cartClientService.addCart(cartRequestToServer);
		redirectAttributes.addFlashAttribute(successMessageKey, "수량이 변경되었습니다");
		return "redirect:/api/cart";
	}

	private static BigDecimal getTotalPrice(List<CartBookResponse> cart) {
		return cart.stream()
			.map(CartBookResponse::totalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
